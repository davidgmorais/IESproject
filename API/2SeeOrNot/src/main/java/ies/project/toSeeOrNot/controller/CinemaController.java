package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.dto.*;
import ies.project.toSeeOrNot.entity.Premier;
import ies.project.toSeeOrNot.entity.Room;
import ies.project.toSeeOrNot.entity.Schedule;
import ies.project.toSeeOrNot.service.CinemaService;
import ies.project.toSeeOrNot.service.UserService;
import ies.project.toSeeOrNot.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Wei
 * @date 2021/1/3 14:18
 */
@RestController
@Transactional
public class CinemaController {

    @Autowired
    CinemaService cinemaService;

    @Autowired
    UserService userService;

    @GetMapping("/common/cinema/{cinemaId}")
    public Result getCinema(@PathVariable("cinemaId") int id){
        CinemaDTO cinema = cinemaService.getCinemaById(id);
        return Result.sucess(cinema);
    }

    @PutMapping("/cinema/change/description/{description}")
    public Result changeDescription(@PathVariable("description") String description, HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        cinemaService.changeDescription(userId, description);
        return Result.sucess("");
    }

    @PostMapping("/cinema/create/room")
    public Result createRoom(@RequestBody Room room, HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        room.setCinema(userId);
        room.setSeats(room.getPositions().size());
        cinemaService.createRoom(room);

        return Result.sucess("");
    }

    @GetMapping("/cinema/rooms")
    public Result getRooms(HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        Set<RoomDTO> roomsByCinema = cinemaService.getRoomsByCinema(userId);

        return roomsByCinema == null ?
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "Can not found any rooms")
                :
                Result.sucess(roomsByCinema);
    }

    @PostMapping("/cinema/create/premier")
    public Result createPremier(@RequestBody Premier premier, HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        premier.setCinema(userId);
        cinemaService.createPremier(premier);
        return Result.sucess("Premier created!");
    }

    @PostMapping("/cinema/create/schedule")
    public Result createSchedule(@RequestBody Schedule schedule, HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        schedule.setId(UUID.randomUUID().toString());
        boolean result = cinemaService.createSchedule(schedule);

        return result ?
                Result.sucess("Schedule created!")
                :
                Result.failure(HttpStatusCode.BAD_REQUEST, "Can not create schedule");
    }

    @DeleteMapping("/cinema/delete/premier/{premierId}")
    public Result deletePremier(@PathVariable("premier") int premier, HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }
        boolean result = cinemaService.deletePremier(premier);
        return result ?
                Result.sucess("Premier deleted!")
                :
                Result.failure(HttpStatusCode.BAD_REQUEST, "Can not delete Premier!");
    }

    @DeleteMapping("/cinema/delete/schedule/{scheduleId}")
    public Result deleteSchedule(@PathVariable("scheduleId") String scheduleId, HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        boolean result = cinemaService.deleteSchedule(scheduleId);

        return result ?
                Result.sucess("Schedule deleted!")
                :
                Result.failure(HttpStatusCode.BAD_REQUEST, "Can not delete Schedule!");
    }

    @GetMapping("/common/premier/{premierId}")
    public Result getPremier(@PathVariable("premierId") int premierId , HttpServletRequest request){

        PremierDTO premier = cinemaService.getPremierById(premierId);
        return premier == null ?
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "It couldn't be found!")
                :
                Result.sucess(premier);
    }


    @GetMapping("/common/schedule/{scheduleId}")
    public Result getSchedule(@PathVariable("scheduleId") String scheduleId, HttpServletRequest request){

        ScheduleDTO schedule = cinemaService.getScheduleById(scheduleId);
        return schedule == null ?
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "It couldn't be found!")
                :
                Result.sucess(schedule);
    }
}
