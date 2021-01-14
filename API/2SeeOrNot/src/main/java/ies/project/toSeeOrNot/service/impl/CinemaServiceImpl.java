package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.common.enums.NoficationType;
import ies.project.toSeeOrNot.component.RedisUtils;
import ies.project.toSeeOrNot.dto.*;
import ies.project.toSeeOrNot.entity.*;
import ies.project.toSeeOrNot.repository.CinemaRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

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
    NotificationService notificationService;

    @Override
    public CinemaDTO getCinemaById(int id) {
        Cinema cinema = cinemaRepository.getCinemaById(id);

        if (cinema == null)
            return null;

        CinemaDTO cinemaDTO = new CinemaDTO();
        BeanUtils.copyProperties(cinema, cinemaDTO);
        cinemaDTO.setUser(userService.getUserById(cinema.getId()));
        cinemaDTO.setRooms(getRoomsByCinema(id));
        cinemaDTO.setPremiers(premierService.getPremiersByCinema(id, 0));
        cinemaDTO.setComments(commentService.getCommentsByCinema(id, 0));
        cinemaDTO.setNotifications(notificationService.getNumberOfNotificationsUnreadByUser(id));

        return cinemaDTO;
    }

    private CinemaDTO getDTO(Cinema cinema){
        CinemaDTO cinemaDTO = new CinemaDTO();
        BeanUtils.copyProperties(cinema, cinemaDTO);
        cinemaDTO.setUser(userService.getUserById(cinema.getId()));
        cinemaDTO.setRooms(getRoomsByCinema(cinema.getId()));
        cinemaDTO.setPremiers(premierService.getPremiersByCinema(cinema.getId(), 0));
        cinemaDTO.setComments(commentService.getCommentsByCinema(cinema.getId(), 0));
        cinemaDTO.setNotifications(notificationService.getNumberOfNotificationsUnreadByUser(cinema.getId()));
        return cinemaDTO;
    }

    @Override
    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    @Override
    public PageDTO<CinemaDTO> getListCinemas(int page) {

        Page<Cinema> cinemas = cinemaRepository.findAll(PageRequest.of(page, 10, Sort.by("followers").descending()));

        Set<CinemaDTO> collect = cinemas.getContent().stream().map(cinema -> {
            CinemaDTO cinemaDTO = new CinemaDTO();
            BeanUtils.copyProperties(cinema, cinemaDTO);
            cinemaDTO.setUser(userService.getUserById(cinema.getId()));
            cinemaDTO.setRooms(getRoomsByCinema(cinema.getId()));
            cinemaDTO.setPremiers(premierService.getPremiersByCinema(cinema.getId(), 0));
            cinemaDTO.setComments(commentService.getCommentsByCinema(cinema.getId(), 0));
            cinemaDTO.setNotifications(notificationService.getNumberOfNotificationsUnreadByUser(cinema.getId()));
            return cinemaDTO;
        }).collect(Collectors.toSet());
        return new PageDTO<>(collect, cinemas.getTotalPages(), cinemas.getTotalElements());
    }

    @Override
    public void changeDescription(int id, String description) {
        cinemaRepository.changeDescription(id, description);
    }

    @Override
    public boolean createRoom(Room room) {
        Room saved = roomService.save(room);
        if (saved == null)
            return false;

        room.getPositions().forEach(
            position ->{
                String[] coordinate = position.split(",");
                Seat seat = new Seat();
                seat.setY(coordinate[0].trim());
                seat.setX(coordinate[1].trim());
                seat.setRoomId(saved.getId());
                seatService.save(seat);
            }
        );

        return true;
    }

    @Override
    public void createPremier(Premier premier) {
        Premier savedPremier = premierService.createPremier(premier);
        UserDTO cinema = userService.getUserById(savedPremier.getCinema());
        Set<User> followedUsers = userService.getFollowedUsersByCinema(savedPremier.getCinema());

        // send notifications to cinema's followers
        followedUsers.forEach(user -> {
            notificationService.createNotification(savedPremier.getCinema(), user.getId(),
                        "Cinema " + cinema.getUserName() + " has new Premier",
                            "",
                    NoficationType.PREMIER,
                    savedPremier.getId());
        });
    }

    @Override
    public synchronized void follow(int cinema) {
        cinemaRepository.follow(cinema);
    }

    @Override
    public synchronized void disfollow(int cinema) {
        cinemaRepository.disfollow(cinema);
    }

    @Override
    public Set<RoomDTO> getRoomsByCinema(int cinema) {
        return roomService.getRoomsByCinema(cinema);
    }

    @Override
    public PremierDTO getPremierById(int premier) {
        return premierService.getPremierById(premier);
    }

    @Override
    public ScheduleDTO getScheduleById(String schedule) {
        return scheduleService.getScheduleById(schedule);
    }

    @Override
    public boolean createSchedule(Schedule schedule) {
        PremierDTO premier = premierService.getPremierById(schedule.getPremier());
        if (scheduleService.hasConflit(schedule)){
            return false;
        }
        Schedule savedSchedule = scheduleService.createSchedule(schedule, premier.getPrice());
        return savedSchedule != null;
    }

    @Override
    public boolean deleteSchedule(String schedule) {
        return scheduleService.delete(schedule);
    }

    @Override
    public boolean deletePremier(int premier) {
        return premierService.delete(premier);
    }

    @Override
    public PageDTO<CinemaDTO> getCinemas(int page) {
        Page<Cinema> all = cinemaRepository.findAll(PageRequest.of(page, 10));
        Set<CinemaDTO> collect = all.getContent().stream().map(this::getDTO).collect(Collectors.toSet());
        return new PageDTO<>(collect, all.getTotalPages(), all.getTotalElements());
    }
}
