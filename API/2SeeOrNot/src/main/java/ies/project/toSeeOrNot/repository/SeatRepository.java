package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Seat;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/3 15:38
 */
public interface SeatRepository extends PagingAndSortingRepository<Seat, Integer> {
    Set<Seat> getSeatsByRoomIdAndFlagFalse(int roomId);

    Seat getSeatById(int id);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM seat WHERE room_id = :room and x = :x and y = :y")
    void removeSeat(int room, String x, String y);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE seat SET flag = 1 WHERE room_id = :room")
    void deleteSeatsByRoom(int room);
}
