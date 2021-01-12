package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.dto.PageDTO;
import ies.project.toSeeOrNot.entity.Film;
import org.springframework.data.domain.Page;
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
    PageDTO<FilmDTO> getFilmsByTitle(String title, Pageable pageable);

    PageDTO<FilmDTO> getFilmsByActorName(String actor, Pageable pageable);

    PageDTO<FilmDTO> getFilmsSortedBy(Pageable pageable);

    FilmDTO getFilmById(String filmId, boolean wantComments);

    PageDTO<FilmDTO> getFilmsByGenre(String genre, Pageable page);

    PageDTO<FilmDTO> getFilmsByDirector(String director, Pageable page);

    PageDTO<FilmDTO> getFilmsByYear(int year, Pageable page);

    PageDTO<FilmDTO> getFavouriteFilmByUser(int user, int page);

    void addFilm(Film film);

    void like(String film);

    void dislike(String film);
}
