package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Wei
 * @date 2020/12/30 22:51
 */
public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {
    Comment getCommentById(int id);
    Page<Comment> getCommentsByCinemaAndFlagFalse(Integer cinema, Pageable page);
    Page<Comment> getCommentsByParentIdAndFlagFalse(Integer parent, Pageable page);
    Page<Comment> getCommentsByFilmAndFlagFalse(String film, Pageable page);
    Page<Comment> getCommentsByPremierAndFlagFalse(Integer premier, Pageable page);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE comment set like = like + 1 where id = :id")
    Comment like(int id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE comment set flag = 1 where id = :id")
    Comment removeComment(int id);
}
