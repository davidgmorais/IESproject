package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.RoomDTO;
import ies.project.toSeeOrNot.entity.Room;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/3 16:34
 */
public interface RoomService {
    Set<RoomDTO> getRoomsByCinema(int cinema);

    RoomDTO getRoomById(int id);

    Room save(Room room);
}
