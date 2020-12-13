package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Genre;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Wei
 * @date 2020/12/10 17:11
 */
public interface GenreRepository extends PagingAndSortingRepository<Genre, String>{
    List<Genre> getGenresByFilm(String film);
}