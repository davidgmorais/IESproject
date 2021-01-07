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

    void changeDescription(int id, String description);

    void createRoom(Room room);

    void createPremier(Premier premier);

    void follow(int cinema);

    void disfollow(int cinema);

    Set<RoomDTO> getRoomsByCinema(int cinema);

    PremierDTO getPremierById(int premier);

    ScheduleDTO getScheduleById(String schedule);

    boolean createSchedule(Schedule schedule);

    boolean deleteSchedule(String schedule);

    boolean deletePremier(int premier);
}
