package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.RoomDTO;
import ies.project.toSeeOrNot.dto.SeatDTO;
import ies.project.toSeeOrNot.entity.Room;
import ies.project.toSeeOrNot.repository.RoomRepository;
import ies.project.toSeeOrNot.service.RoomService;
import ies.project.toSeeOrNot.service.SeatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wei
 * @date 2021/1/3 16:35
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    SeatService seatService;

    @Override
    @Cacheable(value = "room", key = "#root.methodName+'['+#cinema+']'", unless = "#result == null")
    public Set<RoomDTO> getRoomsByCinema(int cinema) {
        Set<Room> rooms = roomRepository.getRoomsByCinema(cinema);
        return fillList(rooms);
    }

    @Override
    @Cacheable(value = "room", key = "#root.methodName+'['+#id+']'", unless = "#result == null")
    public RoomDTO getRoomById(int id) {
        Room room = roomRepository.getRoomById(id);
        RoomDTO roomDTO = new RoomDTO();
        BeanUtils.copyProperties(room, roomDTO);
        roomDTO.setPositions(seatService.getSeatsByRoom(room.getId()));
        return roomDTO;
    }

    private Set<RoomDTO> fillList(Set<Room> rooms){
        if (rooms.size() == 0){
            return null;
        }

        return rooms.stream().map(
                room -> {
                    RoomDTO roomDTO = new RoomDTO();
                    BeanUtils.copyProperties(room, roomDTO);
                    roomDTO.setPositions(seatService.getSeatsByRoom(room.getId()));
                    return roomDTO;
                }
        ).collect(Collectors.toSet());
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }
}
