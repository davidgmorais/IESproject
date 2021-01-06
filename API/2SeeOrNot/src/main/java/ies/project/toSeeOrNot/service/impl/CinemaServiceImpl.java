package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.common.enums.NoficationType;
import ies.project.toSeeOrNot.dto.*;
import ies.project.toSeeOrNot.entity.*;
import ies.project.toSeeOrNot.repository.CinemaRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Set;

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
    @Cacheable(value = "cinema", key = "'cinema:'+#id", unless = "#result == null")
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

    @Override
    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    @Override
    @CachePut(value = "cinema", key = "'cinema:'+#id")
    public CinemaDTO changeDescription(int id, String description) {
        cinemaRepository.changeDescription(id, description);
        // update cache
        CinemaDTO cached = getCinemaById(id);
        cached.setDescription(description);
        return cached;
    }

    @Override
    @CachePut(value = "cinema", key = "'cinema:'+#room.cinema")
    public CinemaDTO createRoom(Room room) {
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

        //update cache
        CinemaDTO cached = getCinemaById(room.getCinema());
        return cached;
    }

    @Override
    @CachePut(value = "cinema", key = "'cinema:'+#premier.cinema")
    public CinemaDTO createPremier(Premier premier) {
        Premier savedPremier = premierService.createPremier(premier);
        UserDTO cinema = userService.getUserById(premier.getCinema());
        Set<Integer> followedUsers = userService.getFollowedUsersByCinema(premier.getCinema());
        // send notifications to cinema's followers
        followedUsers.forEach(user -> {
            notificationService.createNotification(premier.getCinema(), user,
                        "Cinema " + cinema.getUserName() + " has new Premier",
                            "",
                    NoficationType.PREMIER,
                    premier.getId());
        });

        //update cache
          CinemaDTO cached = getCinemaById(premier.getCinema());
        // cached.getPremiers().add(premierService.getPremierById(savedPremier.getId()));
        return cached;
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
    @Cacheable(value = "schedule", key = "'schedule:'+#schedule", unless = "#result == null")
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
}
