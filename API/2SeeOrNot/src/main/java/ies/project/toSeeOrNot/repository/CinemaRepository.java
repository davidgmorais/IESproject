package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Cinema;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Wei
 * @date 2020/12/31 17:47
 */
public interface CinemaRepository extends PagingAndSortingRepository<Cinema, Integer> {
    Cinema getCinemaById(int id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cinema SET description = :description WHERE id = :id")
    void changeDescription(int id, String description);

}
