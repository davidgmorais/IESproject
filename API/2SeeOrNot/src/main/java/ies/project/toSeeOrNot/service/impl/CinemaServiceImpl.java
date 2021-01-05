package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.*;
import ies.project.toSeeOrNot.entity.*;
import ies.project.toSeeOrNot.exception.CinemaNotFoundException;
import ies.project.toSeeOrNot.repository.CinemaRepository;
import ies.project.toSeeOrNot.repository.ScheduleRepository;
import ies.project.toSeeOrNot.repository.SeatRepository;
import ies.project.toSeeOrNot.repository.TicketRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Wei
 * @date 2020/12/31 17:48
 */
@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Autowired
    PremierService premierService;

    @Autowired
    SeatService seatService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public CinemaDTO getCinemaById(int id) {
        Cinema cinema = cinemaRepository.getCinemaById(id);

        if (cinema == null)
            throw new CinemaNotFoundException();

        CinemaDTO cinemaDTO = new CinemaDTO();
        BeanUtils.copyProperties(cinema, cinemaDTO);
        cinemaDTO.setUser(userService.getUserById(cinema.getId()));
        cinemaDTO.setRooms(getRoomsByCinema(id));

        return cinemaDTO;
    }

    @Override
    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    @Override
    public void changeDescription(int id, String description) {
        cinemaRepository.changeDescription(id, description);
    }

    @Override
    public void createRoom(Room room) {
        Room saved = roomService.save(room);

        room.getPositions().forEach(
            position ->{
                String[] coordinate = position.split(",");
                Seat seat = new Seat();
                seat.setRow(coordinate[0].trim());
                seat.setColumn(coordinate[1].trim());
                seat.setRoomId(saved.getId());
                seatService.save(seat);
            }
        );
    }

    @Override
    public void createPremier(Premier premier) {
        Premier save = premierService.save(premier);

        save.getSchedules().forEach(
                schedule -> {
                    schedule.setPremier(save.getId());
                    schedule.setId(UUID.randomUUID().toString());
                    Schedule savedSchedule = scheduleService.save(schedule);

                    Set<SeatDTO> seats = seatService.getSeatsByRoom(schedule.getRoom());

                    seats.forEach(seat-> {
                        Ticket ticket = new Ticket();
                        ticket.setBuyer(-1); // admin's id
                        ticket.setPrice(save.getPrice());
                        ticket.setRoomId(schedule.getRoom());
                        ticket.setSchedule(savedSchedule.getId());
                        ticket.setSeatId(seat.getId());
                        ticket.setSold(false);
                        ticketRepository.save(ticket);
                    });

                }
        );
    }

    @Override
    public Set<RoomDTO> getRoomsByCinema(int cinema) {
        return roomService.getRoomsByCinema(cinema);
    }

    @Override
    public Set<RoomDTO> getRoomsByPremier(int premier) {
        return roomService.getRoomsByPremier(premier);
    }

    @Override
    public PremierDTO getPremierById(int premier) {
        return premierService.getPremierById(premier);
    }

    @Override
    public Set<ScheduleDTO> getSchedulesByPremier(int premier) {
        return scheduleService.getSchedulesByPremier(premier);
    }

    @Override
    public ScheduleDTO getScheduleById(String schedule) {
        return scheduleService.getScheduleById(schedule);
    }

    @Override
    public void createSchedule(Schedule schedule) {
        PremierDTO premier = premierService.getPremierById(schedule.getPremier());
        Schedule savedSchedule = scheduleService.save(schedule);
        Set<SeatDTO> seats = seatService.getSeatsByRoom(schedule.getRoom());

        seats.forEach(seat-> {
            Ticket ticket = new Ticket();
            ticket.setBuyer(-1); // admin's id
            ticket.setPrice(premier.getPrice());
            ticket.setRoomId(schedule.getRoom());
            ticket.setSchedule(savedSchedule.getId());
            ticket.setSeatId(seat.getId());
            ticket.setSold(false);
            ticketRepository.save(ticket);
        });
    }
}
