package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.dto.NotificationDTO;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.service.UserService;
import ies.project.toSeeOrNot.utils.JWTUtils;
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

    @PostMapping("/common/register")
    public Result register(@RequestBody User user){
        User register = userService.register(user);
        if (register == null)
            return Result.failure(HttpStatusCode.USER_ALREADY_EXISTS);

        return Result.sucess(HttpStatusCode.OK);
    }

    @PutMapping("/user/changepwd")
    public Result changePassword(HttpServletRequest request){
        String newPassw = request.getParameter("password");
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));
        User user = userService.changePasswd(userId, newPassw);
        if (user == null)
            return Result.failure(HttpStatusCode.USER_NOT_FOUND);

        return Result.sucess("");
    }

    @GetMapping("/user/notifications")
    public Result getNotifications(@RequestParam(value = "page", defaultValue = "1") Integer page, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        return Result.sucess(userService.notifications(JWTUtils.getUserId(token), PageRequest.of(page - 1, limit)));
    }

    @PostMapping("/user/add_favourite/film")
    public Result addFavouriteFilm(@RequestParam("id") String filmId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        userService.addFavouriteFilm(JWTUtils.getUserId(token), filmId);
        return Result.sucess("");
    }

    @DeleteMapping("/user/remove_favourite/film")
    public Result removeFavouriteFilm(@RequestParam("id") String filmId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        userService.removeFavouriteFilm(JWTUtils.getUserId(token), filmId);
        return Result.sucess("");
    }

}
