package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.component.RedisUtils;
import ies.project.toSeeOrNot.dto.ScheduleDTO;
import ies.project.toSeeOrNot.dto.SeatDTO;
import ies.project.toSeeOrNot.entity.Schedule;
import ies.project.toSeeOrNot.repository.ScheduleRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wei
 * @date 2021/1/3 19:51
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    SeatService seatService;

    @Autowired
    RoomService roomService;

    @Autowired
    TicketService ticketService;

    @Autowired
    PremierService premierService;

    @Autowired
    RedisUtils redisUtils;
    @Override
    public ScheduleDTO getScheduleById(String id) {
        return getDTO(scheduleRepository.getScheduleById(id));
    }

    @Override
    public Set<ScheduleDTO> getSchedulesByPremier(int premier) {
        Set cache = redisUtils.getSet("premier:" + premier + "schedules");
        if (cache != null)
            return cache;

        Set<Schedule> schedules = scheduleRepository.getSchedulesByPremier(premier);
        return schedules.stream().map(this::getDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<Schedule> getSchedulesByRoom(int room) {
        return scheduleRepository.getSchedulesByRoom(room);
    }

    @Override
    public Schedule createSchedule(Schedule schedule, double price) {
        Schedule save = scheduleRepository.save(schedule);
        ticketService.createTickets(save, price);
        return save;
    }

    @Override
    public boolean hasConflit(Schedule schedule) {
        LocalDateTime after = schedule.getStart().minusSeconds(1);
        LocalDateTime before = schedule.getEnd().plusSeconds(1);

        return scheduleRepository.getScheduleByPremierAndRoomAndStartAfterAndEndBefore(schedule.getPremier(), schedule.getRoom(),
                after, before) != null;
    }

    @Override
    public void soldsUpdade(String id, int sold) {
        Schedule schedule = scheduleRepository.getScheduleById(id);
        schedule.setSolds(Math.max(0, schedule.getSolds() + sold));
        scheduleRepository.save(schedule);
    }

    @Override
    public boolean delete(String id) {
        Schedule s = scheduleRepository.getScheduleById(id);
        if (ticketService.deleteTickets(s)){
            scheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void editSchedule(Schedule schedule) {
        scheduleRepository.updateSchedule(schedule);
    }

    @Override
    public void deleteSchedulesByRoom(int room) {
        scheduleRepository.deleteSchedulesByRoom(room);
    }

    private ScheduleDTO getDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        Set<SeatDTO> solds = new HashSet<>();
        Set<SeatDTO> frees = new HashSet<>();
        seatService.getSoldSeatsAndFreeSeats(solds, frees, schedule.getId());

        scheduleDTO.setSold(solds);
        scheduleDTO.setFree(frees);
        scheduleDTO.setRoom(roomService.getRoomById(schedule.getRoom()));
        return scheduleDTO;
    }
}
