package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.dto.NotificationDTO;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Wei
 * @date 2020/12/10 9:17
 */
@RestController
@Transactional
@EnableTransactionManagement
public class UserController {

    private final static Integer limit = 10;

    @Autowired
    @Qualifier("userServiceImpl")
    UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        User register = userService.register(user);
        if (register == null)
            return Result.failure(HttpStatusCode.USER_ALREADY_EXISTS);

        return Result.sucess(HttpStatusCode.OK);
    }

    @PutMapping("/user/changepwd")
    public Result changePassword(@RequestParam("id") Integer userId, HttpServletRequest request){
        String newPassw = request.getParameter("password");
        User user = userService.changePasswd(userId, newPassw);
        if (user == null)
            return Result.failure(HttpStatusCode.USER_NOT_FOUND);

        return Result.sucess("");
    }

    @GetMapping("/user/{userid}/notifications")
    public Result getNotifications(@PathVariable("userid") Integer userid, @RequestParam(value = "page", defaultValue = "0") Integer page){
        return Result.sucess(userService.notifications(userid, PageRequest.of(page, limit)));
    }

    @PostMapping("/user/{userid}/add_favourite/film")
    public Result addFavouriteFilm(@PathVariable("userid") Integer userid, @RequestParam("id") String filmId){
        userService.addFavouriteFilm(userid, filmId);
        return Result.sucess("");
    }

    @PostMapping("/user/{userid}/remove_favourite/film")
    public Result removeFavouriteFilm(@PathVariable("userid") Integer userid, @RequestParam("id") String filmId){
        userService.removeFavouriteFilm(userid, filmId);
        return Result.sucess("");
    }

}
