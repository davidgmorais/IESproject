package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Premier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/31 20:32
 */
public interface PremierRepository extends PagingAndSortingRepository<Premier, Integer> {
    Premier getPremierByIdAndFlagFalse(int id);

    Page<Premier> getPremiersByCinemaAndFlagFalse(int cinema, Pageable page);

    Page<Premier> getPremiersByFilmAndFlagFalse(String film, Pageable page);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE premier SET film = :#{#pr.film}, start = :#{#pr.start}, end = :#{#pr.end}, price = :#{#pr.price} WHERE id = :#{#pr.id}")
    void updatePremier(Premier pr);
}
