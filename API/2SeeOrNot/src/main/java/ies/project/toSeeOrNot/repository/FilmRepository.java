package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Film;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author Wei
 * @date 2020/12/10 9:03
 */
@Repository
public interface FilmRepository extends PagingAndSortingRepository<Film, Integer> {
    List<Film> getFilmsByTitleStartsWith(String title, Pageable page);
}
