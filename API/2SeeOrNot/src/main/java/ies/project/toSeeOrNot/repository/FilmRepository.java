package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Set;

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

    @Query(value = "SELECT new Film(f.title, f.movieId, f.year, f.released, f.runtime, f.director, f.plot, f.likes, f.rating, f.picture) FROM Film f LEFT JOIN StarredIn a on f.movieId = a.film WHERE a.actor = :actorName")
    Page<Film> getFilmsByActor(String actorName, Pageable page);

    Film getFilmByMovieId(String filmId);

    @Query(value = "SELECT new Film(f.title, f.movieId, f.year, f.released, f.runtime, f.director, f.plot, f.likes, f.rating, f.picture) FROM Film f LEFT JOIN FilmByGenre g on f.movieId = g.film WHERE g.genreName = :genre")
    Page<Film> getFilmsByGenre(String genre, Pageable page);

    Page<Film> getFilmsByDirector(String director, Pageable page);

    Page<Film> getFilmsByYearAfterAndYearBefore(int after, int before, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT film * FROM favouritefilm WHERE user = :userid")
    Set<String> getFavouriteFilms(int userid);
}
