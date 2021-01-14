package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.component.RedisUtils;
import ies.project.toSeeOrNot.dto.RoomDTO;
import ies.project.toSeeOrNot.dto.SeatDTO;
import ies.project.toSeeOrNot.entity.Room;
import ies.project.toSeeOrNot.entity.Schedule;
import ies.project.toSeeOrNot.entity.Seat;
import ies.project.toSeeOrNot.exception.DeleteException;
import ies.project.toSeeOrNot.exception.RoomNotFoundException;
import ies.project.toSeeOrNot.repository.RoomRepository;
import ies.project.toSeeOrNot.service.PremierService;
import ies.project.toSeeOrNot.service.RoomService;
import ies.project.toSeeOrNot.service.ScheduleService;
import ies.project.toSeeOrNot.service.SeatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    ScheduleService scheduleService;

    @Override
    public Set<RoomDTO> getRoomsByCinema(int cinema) {
        Set<Room> rooms = roomRepository.getRoomsByCinemaAndFlagFalse(cinema);
        return fillList(rooms);
    }

    @Override
    public RoomDTO getRoomById(int id) {
        Room room = roomRepository.getRoomByIdAndFlagFalse(id);
        if (room == null)
            return null;
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
        Set<Room> roomsByCinema = roomRepository.getRoomsByCinemaAndFlagFalse(room.getCinema());
        for (Room r : roomsByCinema){
            if (r.getName().equals(room.getName()))
                return null;
        }
        return roomRepository.save(room);
    }

    @Override
    public void editRoom(Room room) {
        Set<Schedule> schedules = scheduleService.getSchedulesByRoom(room.getId());

        schedules.forEach(schedule -> {
            if (schedule.getStart().isBefore(LocalDateTime.now()) && schedule.getEnd().isAfter(LocalDateTime.now())){
                throw new RuntimeException("Can not edit a room in use!");
            }
        });

        if (room.getPositions() == null)
            return;

        room.setSeats(room.getPositions().size());
        Set<SeatDTO> seatsByRoom = seatService.getSeatsByRoom(room.getId());
        Set<SeatDTO> collect = seatsByRoom.stream()
                .filter(seatDTO -> !room.getPositions().contains(seatDTO.getX() + "," + seatDTO.getY()))
                .collect(Collectors.toSet());

        collect.forEach(seat -> {
            seatService.delete(room.getId(), seat.getX(), seat.getY());
        });

        Set<String> positions = seatsByRoom.stream().map(seat -> seat.getX() + "," + seat.getY())
                .collect(Collectors.toSet());

        room.getPositions().stream().filter(position -> !positions.contains(position))
                .forEach(position -> seatService.save(new Seat(0, room.getId(), String.valueOf(position.charAt(0)),  String.valueOf(position.charAt(2)), false)));
        roomRepository.updateRoom(room);
    }

    @Override
    public void deleteRoom(int room) {
        Room room2 = roomRepository.getRoomByIdAndFlagFalse(room);
        if (room2 == null)
            throw new RoomNotFoundException();

        Set<Schedule> schedules = scheduleService.getSchedulesByRoom(room);
        LocalDateTime now = LocalDateTime.now();
        schedules.forEach(schedule -> {
            if (schedule.getStart().isBefore(now) && schedule.getEnd().isAfter(now)){
                throw new DeleteException("Can not delete a room in use!");
            }
        });
        roomRepository.deleteRoom(room);
        scheduleService.deleteSchedulesByRoom(room);
        seatService.deleteSeatsByRoom(room);
    }

}
