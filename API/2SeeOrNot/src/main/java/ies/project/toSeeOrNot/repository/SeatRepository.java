package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Seat;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/3 15:38
 */
public interface SeatRepository extends PagingAndSortingRepository<Seat, Integer> {
    Set<Seat> getSeatsByRoomId(int roomId);

    Seat getSeatById(int id);
}
