package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.*;
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

    CinemaDTO changeDescription(int id, String description);

    CinemaDTO createRoom(Room room);

    CinemaDTO createPremier(Premier premier);

    Set<RoomDTO> getRoomsByCinema(int cinema);

    PremierDTO getPremierById(int premier);

    ScheduleDTO getScheduleById(String schedule);

    boolean createSchedule(Schedule schedule);

    boolean deleteSchedule(String schedule);

    boolean deletePremier(int premier);
}
