package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/3 15:27
 */
public interface RoomRepository extends PagingAndSortingRepository<Room, Integer> {
    Set<Room> getRoomsByCinema(int cinema);

    @Query(nativeQuery = true, value = "SELECT room FROM schedule WHERE premier = :premier")
    Set<Integer> getRoomsIdByPremier(int premier);

    Room getRoomById(int id);
}
