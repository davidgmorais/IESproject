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

    @Autowired
    RedisUtils redisUtils;

    @Override
    public CinemaDTO getCinemaById(int id) {
        Object cache = redisUtils.get("cinema:" + id);
        if (cache != null)
            return (CinemaDTO) cache;

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

        redisUtils.add("cinema:" + id, cinemaDTO);
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
        PageDTO<CinemaDTO>  cache = (PageDTO<CinemaDTO>) redisUtils.get("cinemas:" + page);
        if (cache != null)
            return cache;

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
        PageDTO<CinemaDTO> result = new PageDTO<>(collect, cinemas.getTotalPages(), cinemas.getTotalElements());
        redisUtils.add("cinemas:" + page, result);
        return result;
    }

    @Override
    public void changeDescription(int id, String description) {
        cinemaRepository.changeDescription(id, description);

        Object cache = redisUtils.get("cinema:" + id);
        if (cache != null){
            CinemaDTO cinemaDTO = (CinemaDTO) cache;
            cinemaDTO.setDescription(description);
            redisUtils.add("cinema:" + id, cinemaDTO);
        }
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
        RoomDTO roomDTO = null;

        Object cache = redisUtils.get("cinema:" + room.getCinema());
        if (cache != null){
            CinemaDTO cinemaDTO = (CinemaDTO) cache;
            roomDTO = roomService.getRoomById(saved.getId());
            cinemaDTO.getRooms().add(roomDTO);
            redisUtils.add("cinema:" + room.getCinema(), cinemaDTO);
        }

        cache = redisUtils.get("cinema:" + room.getCinema() + ":rooms");
        if (cache != null){
            Set<RoomDTO> rooms = (Set<RoomDTO>) cache;
            if (roomDTO != null){
                rooms.add(roomDTO);
            }else{
                rooms.add(roomService.getRoomById(saved.getId()));
            }
            redisUtils.add("cinema:" + room.getCinema() + ":rooms", rooms);
        }
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

        Object cache = redisUtils.get("cinema:" + premier.getCinema());
        if (cache != null){
            CinemaDTO cinemaDTO = (CinemaDTO) cache;
            PageDTO<PremierDTO> premiers = cinemaDTO.getPremiers();
            if (premiers.getTotalElements() < premiers.getTotalPages()){ // if the first page is not full
                cinemaDTO.getPremiers().getData().add(premierService.getPremierById(savedPremier.getId()));
            }else{
                PageDTO<PremierDTO>  cachedPremiersByPage = (PageDTO<PremierDTO>) redisUtils.get("cinema:" + cinema + ":premiers:" + (premiers.getTotalPages() - 1));
                if (cachedPremiersByPage != null && cachedPremiersByPage.getTotalElements() < 10 * (cachedPremiersByPage.getTotalPages()) - 1){
                    // if the last page is not full
                    cachedPremiersByPage.getData().add(premierService.getPremierById(savedPremier.getId()));
                    redisUtils.add("cinema:" + cinema + ":premiers:" + (premiers.getTotalPages() - 1), cachedPremiersByPage);
                }
            }
            redisUtils.add("cinema:" + premier.getCinema(), cinemaDTO);
        }
    }

    @Override
    public synchronized void follow(int cinema) {
        cinemaRepository.follow(cinema);

        Object cache = redisUtils.get("cinema:" + cinema);
        if (cache != null){
            CinemaDTO cinemaDTO = (CinemaDTO) cache;
            cinemaDTO.setFollowers(cinemaDTO.getFollowers() + 1);
            redisUtils.add("cinema:" + cinema, cinemaDTO);
        }
    }

    @Override
    public synchronized void disfollow(int cinema) {
        cinemaRepository.disfollow(cinema);
        Object cache = redisUtils.get("cinema:" + cinema);
        if (cache != null){
            CinemaDTO cinemaDTO = (CinemaDTO) cache;
            cinemaDTO.setFollowers(cinemaDTO.getFollowers() - 1);
            redisUtils.add("cinema:" + cinema, cinemaDTO);
        }
    }

    @Override
    public Set<RoomDTO> getRoomsByCinema(int cinema) {
        Set<RoomDTO> cache = redisUtils.getSet("cinema:" + cinema + ":rooms");
        if (cache != null){
            return cache;
        }

        Set<RoomDTO> rooms = roomService.getRoomsByCinema(cinema);
        redisUtils.storeSet("cinema:" + cinema + ":rooms", rooms);
        return rooms;
    }

    @Override
    public PremierDTO getPremierById(int premier) {
        Object cache = redisUtils.get("premier:" + premier);
        if (cache != null)
            return (PremierDTO) cache;

        PremierDTO premierById = premierService.getPremierById(premier);
        redisUtils.add("premier:" + premier, premierById);
        return premierById;
    }

    @Override
    public ScheduleDTO getScheduleById(String schedule) {
        Object cache = redisUtils.get("schedule:" + schedule);
        if (cache != null)
            return (ScheduleDTO) cache;

        ScheduleDTO scheduleById = scheduleService.getScheduleById(schedule);
        redisUtils.add("schedule:" + schedule, scheduleById);
        return scheduleById;
    }

    @Override
    public boolean createSchedule(Schedule schedule) {
        PremierDTO premier = premierService.getPremierById(schedule.getPremier());
        if (scheduleService.hasConflit(schedule)){
            return false;
        }
        Schedule savedSchedule = scheduleService.createSchedule(schedule, premier.getPrice());
        Object cache = redisUtils.get("premier:" + schedule.getPremier());
        if (cache != null){
            PremierDTO premierDTO = (PremierDTO) cache;
            premierDTO.getSchedules().add(scheduleService.getScheduleById(savedSchedule.getId()));
            redisUtils.add("premier:" + schedule.getPremier(), premierDTO);
        }

        return savedSchedule != null;
    }

    @Override
    public boolean deleteSchedule(String schedule) {
        Object cache = redisUtils.get("schedule:" + schedule);
        if (cache != null){
            ScheduleDTO scheduleDTO = (ScheduleDTO) cache;
            Object premier = redisUtils.get("premier:" + scheduleDTO.getPremier());
            if (premier != null){
                PremierDTO premier1 = (PremierDTO) premier;
                premier1.getSchedules().remove(scheduleDTO);
                redisUtils.add("premier:" + scheduleDTO.getPremier(), premier1);

                Object cinema = redisUtils.get("cinema:" + premier1.getCinema().getId());
                if (cinema != null){
                    CinemaDTO cinema1 = (CinemaDTO) cinema;
                    cinema1.getPremiers().getData().add(premier1);
                    redisUtils.add("cinema:" + premier1.getCinema().getId(), cinema1);
                }

                Set schedules = redisUtils.getSet("premier:" + premier + "schedules");
                if (schedules != null){
                    schedules.remove(scheduleDTO);
                    redisUtils.storeSet("premier:" + premier + "schedules", schedules);
                }
            }
        }

        redisUtils.del("schedule:" + schedule);
        return scheduleService.delete(schedule);
    }

    @Override
    public boolean deletePremier(int premier) {
        PremierDTO premierDTO = (PremierDTO) redisUtils.get("premier:" + premier);
        if (premierDTO != null){
            CinemaDTO cinemaDTO = (CinemaDTO) redisUtils.get("cinema:" + premierDTO.getCinema().getId());
            if (cinemaDTO != null){
                cinemaDTO.getPremiers().getData().remove(premierDTO);
                redisUtils.add("cinema:" + premierDTO.getCinema().getId(), cinemaDTO);
            }


            Set premiers = redisUtils.getSet("cinema:" + premierDTO.getCinema().getId() + ":premier");
            if (premiers != null){
                premiers.remove(premierDTO);
                redisUtils.storeSet("cinema:" + premierDTO.getCinema().getId() + ":premier", premiers);
            }
        }
        redisUtils.del("premier:" + premier);
        return premierService.delete(premier);
    }

    @Override
    public PageDTO<CinemaDTO> getCinemas(int page) {
        Page<Cinema> all = cinemaRepository.findAll(PageRequest.of(page, 10));
        Set<CinemaDTO> collect = all.getContent().stream().map(this::getDTO).collect(Collectors.toSet());
        return new PageDTO<>(collect, all.getTotalPages(), all.getTotalElements());
    }
}
