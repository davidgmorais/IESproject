package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.entity.Film;
import org.springframework.data.domain.Pageable;
import java.util.Set;

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
    Set<FilmDTO> getFilmsByTitle(String title, Pageable pageable);

    Set<FilmDTO> getFilmsByActorName(String actor, Pageable pageable);

    Set<FilmDTO> getFilmsSortedBy(Pageable pageable);

    FilmDTO getFilmById(String filmId, boolean wantComments);

    Set<FilmDTO> getFilmsByGenre(String genre, Pageable page);

    Set<FilmDTO> getFilmsByDirector(String director, Pageable page);

    Set<FilmDTO> getFilmsByYear(int year, Pageable page);

    Set<FilmDTO> getFavouriteFilmByUser(int user);

    void addFilm(Film film);
}
