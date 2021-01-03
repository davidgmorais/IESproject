package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.ScheduleDTO;
import ies.project.toSeeOrNot.dto.SeatDTO;
import ies.project.toSeeOrNot.entity.Schedule;
import ies.project.toSeeOrNot.repository.ScheduleRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
    PremierService premierService;

    @Override
    public ScheduleDTO getScheduleById(String id) {
        return getDTO(scheduleRepository.getScheduleById(id));
    }

    @Override
    public Set<ScheduleDTO> getSchedulesByPremier(int premier) {
        Set<Schedule> schedules = scheduleRepository.getSchedulesByPremier(premier);
        Set<ScheduleDTO> result = new HashSet<>();
        schedules.forEach(
                schedule -> {
                    result.add(getDTO(schedule));
                }
        );
        return result;
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public ScheduleDTO getScheduleByPremierAndRoom(int premier, int room) {
        return getDTO(scheduleRepository.getScheduleByPremierAndRoom(premier, room));
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
