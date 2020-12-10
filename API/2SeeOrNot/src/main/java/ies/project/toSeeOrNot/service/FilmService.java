package ies.project.toSeeOrNot.service;

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
    List<Film> getFilmByTitle(String title, Pageable pageable);
}
