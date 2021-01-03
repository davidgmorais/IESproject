package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
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
        return Result.sucess(cinemaService.getCinemaById(id));
    }

    @PutMapping("/cinema/change/description")
    public Result changeDescription(@RequestParam("description") String description, HttpServletRequest request){
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

        return Result.sucess(cinemaService.getRoomsByCinema(userId));
    }

    @PostMapping("/cinema/create/premier")
    public Result createPremier(@RequestBody Premier premier, HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        premier.setCinema(userId);
        cinemaService.createPremier(premier);
        return Result.sucess("");
    }

    @PostMapping("/cinema/create/schedule}")
    public Result createSchedule(@RequestBody Schedule schedule, HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        schedule.setId(UUID.randomUUID().toString());
        cinemaService.createSchedule(schedule);
        return Result.sucess("");
    }

    @GetMapping("/cinema/premier/{premierId}")
    public Result getPremier(@PathVariable("premierId") int premierId , HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        return Result.sucess(cinemaService.getPremierById(premierId));
    }

    @GetMapping("/cinema/schedule/{scheduleId}")
    public Result getSchedule(@PathVariable("scheduleId") String scheduleId, HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));

        if (!userService.isCinema(userId)){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        return Result.sucess(cinemaService.getScheduleById(scheduleId));
    }
}
