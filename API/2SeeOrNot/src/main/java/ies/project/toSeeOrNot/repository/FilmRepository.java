package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Actor;
import ies.project.toSeeOrNot.entity.Film;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wei
 * @date 2020/12/10 9:03
 */
@Repository
public interface FilmRepository extends PagingAndSortingRepository<Film, String> {
    /**
     * get all fimls with title starts with {@param title}
     * @param title title
     * @param page  page
     * @return
     */
    Page<Film> getFilmsByTitleStartsWith(String title, Pageable page);

    /**
     * find all films and sort by (or not) given information
     * @param page an object that contains informations for pages, and sort by
     * @return
     */
    Page<Film> findAll(Pageable page);

    @Query(value = "SELECT new Film(f.title, f.movieId, f.year, f.released, f.runtime, f.director, f.plot, f.like, f.rating, f.picture) FROM Film f LEFT JOIN Actor a on f.movieId = a.film WHERE a.actor = :actorName")
    Page<Film> getFilmsByActor(String actorName, Pageable page);

    Film getFilmByMovieId(String filmId);

    @Query(value = "SELECT new Film(f.title, f.movieId, f.year, f.released, f.runtime, f.director, f.plot, f.like, f.rating, f.picture) FROM Film f LEFT JOIN Genre g on f.movieId = g.film WHERE g.genreName = :genre")
    Page<Film> getFilmsByGenre(String genre, Pageable page);

    Page<Film> getFilmsByDirector(String director, Pageable page);

    Page<Film> getFilmsByYear(Integer year, Pageable page);
}
