package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.CommentDTO;
import ies.project.toSeeOrNot.dto.PageDTO;
import ies.project.toSeeOrNot.entity.Comment;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/30 22:53
 */
public interface CommentService {
    Comment createComment(Comment comment);

    PageDTO<CommentDTO> getCommentsByFilm(String filmId, int page);

    PageDTO<CommentDTO> getCommentsByPremier(int premierId, int page);

    PageDTO<CommentDTO> getCommentsByCinema(int cinemaId, int page);

    PageDTO<CommentDTO> getCommentsByParentId(int parentId, int page);

    boolean like(int currentUser, int comment);

    boolean removeComment(int currenUser, int comment);

    int getNumberOfCommentsByCinema(int cinema);

    int getNumberOfCommentsByParentId(int parent);

    int getNumberOfCommentsByFilm(String film);

    int getNumberOfCommentsByPremier(int premier);
}
