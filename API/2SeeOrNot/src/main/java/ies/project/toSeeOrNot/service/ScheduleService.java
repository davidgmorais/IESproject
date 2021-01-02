package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.ScheduleDTO;
import ies.project.toSeeOrNot.entity.Schedule;

/**
 * @author Wei
 * @date 2020/12/31 18:06
 */
public interface ScheduleService {
    ScheduleDTO getScheduleById(int id);
}
