package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.CommentDTO;
import ies.project.toSeeOrNot.entity.Comment;

import java.util.List;

/**
 * @author Wei
 * @date 2020/12/30 22:53
 */
public interface CommentService {
    Comment createComment(Comment comment);
    List<CommentDTO> getCommentsByFilm(String filmId, int page);
    List<CommentDTO> getCommentsByPremier(int premierId, int page);
    List<CommentDTO> getCommentsByCinema(int cinemaId, int page);
    void like(int currentUser, int comment);
    void removeComment(int currenUser, int comment);
}
