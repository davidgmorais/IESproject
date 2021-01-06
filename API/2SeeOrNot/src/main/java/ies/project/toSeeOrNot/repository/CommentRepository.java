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

    Page<Comment> getCommentsByCinemaAndFlagFalseAndParentId(int cinema, int parentId, Pageable page);
    Page<Comment> getCommentsByParentIdAndFlagFalse(int parent, Pageable page);
    Page<Comment> getCommentsByFilmAndFlagFalseAndParentId(String film, int parentId, Pageable page);
    Page<Comment> getCommentsByPremierAndFlagFalseAndParentId(int premier, int parentId, Pageable page);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE comment set like = like + 1 where id = :id")
    Comment like(int id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE comment set flag = 1 where id = :id")
    void removeComment(int id);

    @Query(nativeQuery = true, value = "SELECT COUNT(1) FROM comment where cinema = :cinema")
    int getNumberOfCommentsByCinema(int cinema);

    @Query(nativeQuery = true, value = "SELECT COUNT(1) FROM comment where parent = :parent")
    int getNumberOfCommentsByParentId(int parent);

    @Query(nativeQuery = true, value = "SELECT COUNT(1) FROM comment where film = :film")
    int getNumberOfCommentsByFilm(String film);

    @Query(nativeQuery = true, value = "SELECT COUNT(1) FROM comment where premier = :premier")
    int getNumberOfCommentsByPremier(int premier);
}
