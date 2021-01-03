package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Cinema;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Wei
 * @date 2020/12/31 17:47
 */
public interface CinemaRepository extends PagingAndSortingRepository<Cinema, Integer> {
    Cinema getCinemaById(int id);
}
