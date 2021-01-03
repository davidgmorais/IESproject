package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.CinemaDTO;
import ies.project.toSeeOrNot.dto.PremierDTO;
import ies.project.toSeeOrNot.dto.RoomDTO;
import ies.project.toSeeOrNot.dto.ScheduleDTO;
import ies.project.toSeeOrNot.entity.Cinema;
import ies.project.toSeeOrNot.entity.Premier;
import ies.project.toSeeOrNot.entity.Room;
import ies.project.toSeeOrNot.entity.Schedule;

import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/31 17:48
 */
public interface CinemaService {
    CinemaDTO getCinemaById(int id);

    void save(Cinema cinema);

    void changeDescription(int id, String description);

    void createRoom(Room room);

    void createPremier(Premier premier);

    Set<RoomDTO> getRoomsByCinema(int cinema);

    Set<RoomDTO> getRoomsByPremier(int premier);

    PremierDTO getPremierById(int premier);

    Set<ScheduleDTO> getSchedulesByPremier(int premier);

    ScheduleDTO getScheduleById(String schedule);

    void createSchedule(Schedule schedule);
}
