package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Schedule;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Wei
 * @date 2020/12/31 18:06
 */
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Integer> {
    Schedule getScheduleById(int id);
}
