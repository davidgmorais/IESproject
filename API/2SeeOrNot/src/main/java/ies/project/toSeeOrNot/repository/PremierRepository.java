package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Premier;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Wei
 * @date 2020/12/31 20:32
 */
public interface PremierRepository extends PagingAndSortingRepository<Premier, Integer> {
    Premier getPremierById(int id);
}
