package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.FilmByGenre;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Wei
 * @date 2020/12/10 17:11
 */
public interface GenreRepository extends PagingAndSortingRepository<FilmByGenre, String>{
    List<FilmByGenre> getGenresByFilm(String film);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO genre VALUES(:genre)")
    void saveGenre(String genre);

    @Query(nativeQuery = true, value = "SELECT count(1) FROM genre WHERE genre_name = :genre")
    int getGenre(String genre);
}
