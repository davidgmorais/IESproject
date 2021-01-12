package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.common.enums.NoficationType;
import ies.project.toSeeOrNot.component.RedisUtils;
import ies.project.toSeeOrNot.dto.*;
import ies.project.toSeeOrNot.entity.Comment;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.exception.*;
import ies.project.toSeeOrNot.repository.CommentRepository;
import ies.project.toSeeOrNot.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Wei
 * @date 2020/12/30 22:54
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    FilmService filmService;

    @Autowired
    CinemaService cinemaService;

    @Autowired
    PremierService premierService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    NotificationService notificationService;

    public Comment createComment(Comment comment) {
        if (comment.getContent() != null && comment.getContent().length() < 300){
            String film = comment.getFilm();
            int parent = comment.getParentId();
            int author = comment.getAuthor();
            int cinema = comment.getCinema();
            int premier = comment.getPremier();
            int replyto = comment.getReplyto();

            if ( ( StringUtils.hasLength(film) && (cinema + premier) != 0 ) // film is not a empty string and at least one of [cinema, premier] is not zero
                || ( !StringUtils.hasLength(film) && (cinema | premier) != (cinema + premier) ) ){ // film is a empty string and both of [cinema, premier] are not zero
                throw new InvalidCommentException("Can not reply to multiple entity at same time!");
            }

            if (!StringUtils.hasLength(film) || filmService.getFilmById(film, false, false) == null){
                throw new FilmNotFoundException();
            }

            if (parent != 0 && commentRepository.getCommentById(parent) == null){
                throw new CommentNotFoundException();
            }

            if (replyto == author){
                throw new InvalidCommentException("Can not reply to your self!");
            }

            if (premier != 0 && premierService.getPremierById(premier) != null){
                throw new PremierNotFoundException();
            }

            if (cinema != 0 && !userService.isCinema(cinema)) {
                throw new UserNotFoundException("User " + cinema +" is not a Cinema!");
            }

            UserDTO authorDTO = userService.getUserById(author);

            if (replyto != 0) {
                UserDTO replytoUser = userService.getUserById(replyto);
                if (replytoUser == null)
                    throw new UserNotFoundException();

                Comment save = commentRepository.save(comment);

                notificationService.createNotification(author,
                        replytoUser.getId(),
                        authorDTO.getUserName() + " replied to your comment" ,
                        save.getContent(),
                        NoficationType.COMMENT,
                        save.getParentId() == 0 ? save.getId() : save.getParentId());
                return save;
            }
            Comment save = commentRepository.save(comment);

            if(!StringUtils.hasLength(film)){
                notificationService.createNotification(author,
                        parent == 0 ? Math.max(cinema, premier) : parent,
                        authorDTO.getUserName() + " left a comment for you" ,
                        save.getContent(),
                        NoficationType.COMMENT,
                        save.getParentId() == 0 ? save.getId() : save.getParentId());
            }
            return save;

        }
        return null;
    }

    @Override
    public PageDTO<CommentDTO> getCommentsByParentId(int parentId, int page){
        page = Math.max(page, 0);
        Page<Comment> commentsByParentId = commentRepository.getCommentsByParentIdAndFlagFalse(parentId,
                PageRequest.of(page, 10, Sort.by("likes", "created").descending()));
        Set<CommentDTO> comments = fillList(commentsByParentId.getContent());
        return new PageDTO<>(comments, commentsByParentId.getTotalPages(), commentsByParentId.getTotalElements());
    }

    @Override
    public PageDTO<CommentDTO> getCommentsByFilm(String film, int page){
        page = Math.max(page, 0);
        FilmDTO filmById = filmService.getFilmById(film, false, false);
        if (filmById == null)
            throw new FilmNotFoundException();

        Page<Comment> commentsByParentId = commentRepository.getCommentsByFilmAndFlagFalseAndParentId(film, 0,
                PageRequest.of(page, 15, Sort.by("likes", "created").descending()));
        Set<CommentDTO> comments = fillList(commentsByParentId.getContent());
        return new PageDTO<>(comments, commentsByParentId.getTotalPages(), commentsByParentId.getTotalElements());
    }

    @Override
    public PageDTO<CommentDTO> getCommentsByCinema(int cinema, int page){
        page = Math.max(page, 0);
        if (!userService.isCinema(cinema))
            throw new CinemaNotFoundException();

        Page<Comment> commentsByParentId = commentRepository.getCommentsByCinemaAndFlagFalseAndParentId(cinema, 0,
                PageRequest.of(page, 15, Sort.by("likes", "created").descending()));
        Set<CommentDTO> comments = fillList(commentsByParentId.getContent());
        return new PageDTO<>(comments, commentsByParentId.getTotalPages(), commentsByParentId.getTotalElements());
    }

    @Override
    public boolean like(int currentUser, int id) {
        Comment like = commentRepository.like(id);
        if (like == null)
            throw new CommentNotFoundException();

        UserDTO user = userService.getUserById(currentUser);
        
        if (currentUser == like.getAuthor())
            return true;
        // Comment like is a comment of this cinema
        if (userService.isCinema(like.getCinema())){

            notificationService.createNotification(currentUser,
                    like.getAuthor(),
                    user.getUserName() + " liked your comment" ,
                    "",
                    like.getParentId() == 0 ? NoficationType.CINEMA : NoficationType.COMMENT,
                    like.getParentId() == 0 ? like.getCinema() : like.getId());
            return true;
        }

        PremierDTO premier = premierService.getPremierById(like.getPremier());
        if (premier != null){
            notificationService.createNotification(currentUser,
                    like.getAuthor(),
                    user.getUserName() + " liked your comment" ,
                    "",
                    like.getParentId() == 0 ? NoficationType.PREMIER : NoficationType.COMMENT,
                    like.getParentId() == 0 ? like.getPremier() : like.getId());
            return true;
        }
        throw new PremierNotFoundException();
    }

    @Override
    public boolean removeComment(int currenUser, int comment) {
        Comment commentById = commentRepository.getCommentById(comment);
        if (commentById == null){
            throw new CommentNotFoundException("Can't remove the comment! It doesn't exist or it has been removed!");
        }

        if (currenUser != commentById.getAuthor()){
            // -1, id of admin
            if (StringUtils.hasLength(commentById.getFilm()) && currenUser != -1){
                throw new AccessDeniedException("Access denied");
            }

            if (commentById.getCinema() != 0 && currenUser != commentById.getCinema()){
                throw new AccessDeniedException("Access denied");
            }

            if (commentById.getPremier() != 0){
                PremierDTO premierById = premierService.getPremierById(commentById.getPremier());

                if (currenUser != premierById.getCinema().getId()){
                    throw new AccessDeniedException("Access denied");
                }
            }
        }

        commentRepository.removeComment(comment);
        return true;
    }

    @Override
    public int getNumberOfCommentsByCinema(int cinema) {
        return commentRepository.getNumberOfCommentsByCinema(cinema);
    }

    @Override
    public int getNumberOfCommentsByParentId(int parent) {
        return commentRepository.getNumberOfCommentsByParentId(parent);
    }

    @Override
    public int getNumberOfCommentsByFilm(String film) {
        return commentRepository.getNumberOfCommentsByFilm(film);
    }

    @Override
    public int getNumberOfCommentsByPremier(int premier) {
        return commentRepository.getNumberOfCommentsByPremier(premier);
    }

    @Override
    public PageDTO<CommentDTO> getCommentsByPremier(int premier, int page){
        Page<Comment> commentsByParentId = commentRepository.getCommentsByPremierAndFlagFalseAndParentId(premier, 0,
                PageRequest.of(page, 15, Sort.by("likes", "created").descending()));
        Set<CommentDTO> comments = fillList(commentsByParentId.getContent());
        return new PageDTO<>(comments, commentsByParentId.getTotalPages(), commentsByParentId.getTotalElements());
    }

    private Set<CommentDTO> fillList(List<Comment> comments){
        if (comments.size() == 0){
            return null;
        }

        Set<CommentDTO> result = new HashSet<>();

        for (Comment comment : comments){
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setAuthor(userService.getUserById(comment.getAuthor()));
            commentDTO.setReplyto(userService.getUserById(comment.getReplyto()));

            if (comment.getParentId() != 0){
                commentDTO.setSubComments(getNumberOfCommentsByParentId(comment.getParentId()));
            }

            result.add(commentDTO);
        }

        return result;
    }

}
