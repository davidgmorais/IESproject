package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.CommentDTO;
import ies.project.toSeeOrNot.entity.Comment;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/30 22:53
 */
public interface CommentService {
    Comment createComment(Comment comment);

    Set<CommentDTO> getCommentsByFilm(String filmId, int page);

    Set<CommentDTO> getCommentsByPremier(int premierId, int page);

    Set<CommentDTO> getCommentsByCinema(int cinemaId, int page);

    Set<CommentDTO> getCommentsByParentId(int parentId, int page);

    void like(int currentUser, int comment);

    void removeComment(int currenUser, int comment);

    int getNumberOfCommentsByCinema(int cinema);

    int getNumberOfCommentsByParentId(int parent);

    int getNumberOfCommentsByFilm(String film);

    int getNumberOfCommentsByPremier(int premier);
}
