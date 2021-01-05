package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Schedule;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/31 18:06
 */
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, String> {
    Schedule getScheduleById(String id);

    Set<Schedule> getSchedulesByPremier(int premier);

    Schedule getScheduleByPremierAndRoom(int premier, int room);
}
