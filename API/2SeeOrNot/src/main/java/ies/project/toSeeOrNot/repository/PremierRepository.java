package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Premier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/31 20:32
 */
public interface PremierRepository extends PagingAndSortingRepository<Premier, Integer> {
    Premier getPremierById(int id);

    Page<Premier> getPremierByCinema(int cinema, Pageable page);

    Page<Premier> getPremierByFilm(String film, Pageable page);

}
