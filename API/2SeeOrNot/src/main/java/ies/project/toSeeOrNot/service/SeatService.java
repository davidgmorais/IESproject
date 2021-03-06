package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.PageDTO;
import ies.project.toSeeOrNot.dto.SeatDTO;
import ies.project.toSeeOrNot.entity.Seat;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/3 16:44
 */
public interface SeatService {
    Set<SeatDTO> getSeatsByRoom(int room);

    SeatDTO getSeatById(int id);

    void save(Seat seat);

    void getSoldSeatsAndFreeSeats(Set<SeatDTO> sold, Set<SeatDTO> free, String schedule);

    void delete(int room, String x, String y);

    void deleteSeatsByRoom(int room);
}
