package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Schedule;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/31 18:06
 */
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, String> {
    Schedule getScheduleById(String id);

    Schedule getScheduleByPremierAndRoomAndStartAfterAndEndBefore(int premier, int room, LocalDateTime start, LocalDateTime Before);

    Set<Schedule> getSchedulesByPremier(int premier);

    Set<Schedule> getSchedulesByRoom(int room);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE schedule SET start = :#{#sc.start}, end = :#{#sc.end} WHERE uuid = :#{#sc.id}")
    void updateSchedule(Schedule sc);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE schedule SET flag = 1 WHERE room = :room")
    void deleteSchedulesByRoom(int room);
}
