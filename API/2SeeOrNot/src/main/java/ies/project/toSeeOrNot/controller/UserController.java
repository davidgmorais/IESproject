package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.config.RabbitMQConfig;
import ies.project.toSeeOrNot.dto.CinemaUser;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.service.UserService;
import ies.project.toSeeOrNot.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/common/register")
    public Result register(HttpServletResponse response, @RequestBody User user){
        if (userService.isExiste(user.getUserEmail())){
            return Result.failure(HttpStatusCode.USER_ALREADY_EXISTS);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getUserEmail());
        map.put("password", user.getPassword());
        map.put("username", user.getUserName());
        // generate a random number, 100000 <= number <= 999999
        map.put("verifycode", new Random(System.currentTimeMillis()).nextInt(899999) + 100000);
        String token = JWTUtils.createToken(map, 60 * 5);
        response.setHeader("registerToken", token);

        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.REGISTER_ROUTING_KEY, map);

        return Result.sucess(HttpStatusCode.OK);
    }

    @PostMapping("/common/register/cinema")
    public Result registerCinema(HttpServletResponse response, @RequestBody CinemaUser user){
        if (userService.isExiste(user.getUserEmail())){
            return Result.failure(HttpStatusCode.USER_ALREADY_EXISTS);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getUserEmail());
        map.put("password", user.getPassword());
        map.put("username", user.getUserName());
        map.put("location", user.getLocation());
        // generate a random number, 100000 <= number <= 999999
        map.put("verifycode", new Random(System.currentTimeMillis()).nextInt(899999) + 100000);
        String token = JWTUtils.createToken(map, 60 * 5);
        response.setHeader("registerToken", token);

        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.REGISTER_ROUTING_KEY, map);

        return Result.sucess(HttpStatusCode.OK);
    }

    @PostMapping("/common/confirm/{code}")
    public Result confirmRegister(HttpServletRequest request, @PathVariable("code") String code){
        String token = request.getHeader("registerToken");
        if (token == null){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        Claims tokenBody = JWTUtils.getTokenBody(token);
        String verifycode = tokenBody.get("verifycode").toString();
        if (!verifycode.equals(code.trim())){
            return Result.failure(HttpStatusCode.BAD_REQUEST, "Verify code does not matched!", null);
        }

        User user = new User();
        user.setPassword((String) tokenBody.get("password"));
        user.setUserEmail((String) tokenBody.get("email"));
        user.setUserName((String) tokenBody.get("username"));
        User result = userService.register(user);

        return result == null ? Result.failure(HttpStatusCode.USER_ALREADY_EXISTS, "Try again", null) : Result.sucess(HttpStatusCode.OK);
    }

    @PutMapping("/user/changepwd")
    public Result changePassword(HttpServletRequest request){
        String newPassw = request.getParameter("password");
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));
        User user = userService.changePasswd(userId, newPassw);
        if (user == null)
            return Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND);

        return Result.sucess("");
    }

    @GetMapping("/user/notifications")
    public Result getNotifications(@RequestParam(value = "page", defaultValue = "1") Integer page, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        if (token == null){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }
        return Result.sucess(userService.notifications(JWTUtils.getUserId(token), PageRequest.of(page - 1, limit, Sort.by("read").ascending().and(Sort.by("created").descending()))));
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
