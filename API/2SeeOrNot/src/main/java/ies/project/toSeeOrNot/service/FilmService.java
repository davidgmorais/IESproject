package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.entity.Film;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Wei
 * @date 2020/12/10 9:07
 */
public interface FilmService {

    /**
     * search films by title
     * @param title title
     * @return all fimls with a title that starts with {@param title}
     */
    List<FilmDTO> getFilmsByTitle(String title, Pageable pageable);

    List<FilmDTO> getFilmsByActorName(String actor, Pageable pageable);

    List<FilmDTO> getFilmsSortedBy(Pageable pageable);

    FilmDTO getFilmById(String filmId);

    List<FilmDTO> getFilmsByGenre(String genre, Pageable page);

    List<FilmDTO> getFilmsByDirector(String director, Pageable page);

    List<FilmDTO> getFilmsByYear(Integer year, Pageable page);
}