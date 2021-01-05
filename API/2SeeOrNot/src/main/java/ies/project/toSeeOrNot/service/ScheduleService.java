package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.ScheduleDTO;
import ies.project.toSeeOrNot.entity.Schedule;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/3 19:50
 */
public interface ScheduleService {

    ScheduleDTO getScheduleById(String id);

    Set<ScheduleDTO> getSchedulesByPremier(int premier);

    Schedule save(Schedule schedule);

    ScheduleDTO getScheduleByPremierAndRoom(int premier, int room);
}
