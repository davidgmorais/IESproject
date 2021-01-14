package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Room;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/3 15:27
 */
public interface RoomRepository extends PagingAndSortingRepository<Room, Integer> {
    Set<Room> getRoomsByCinemaAndFlagFalse(int cinema);

    Room getRoomByIdAndFlagFalse(int id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE room SET seats = :#{#room.seats} AND name = :#{#room.name} WHERE id = :#{#room.id}")
    void updateRoom(Room room);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE room SET flag = 1 WHERE id = :room")
    void deleteRoom(int room);
}
