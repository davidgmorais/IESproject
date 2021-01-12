package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.config.RabbitMQConfig;
import ies.project.toSeeOrNot.dto.CinemaUser;
import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.dto.PaymentDTO;
import ies.project.toSeeOrNot.dto.UserDTO;
import ies.project.toSeeOrNot.entity.Cinema;
import ies.project.toSeeOrNot.entity.RegisterRequest;
import ies.project.toSeeOrNot.entity.Ticket;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.service.*;
import ies.project.toSeeOrNot.utils.JSONUtils;
import ies.project.toSeeOrNot.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
    UserService userService;

    @Autowired
    CinemaService cinemaService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    RegisterRequestService registerRequestService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("/common/login")
    public Result login(HttpServletRequest request, HttpServletResponse response){
        String token = (String) request.getAttribute("token");
        UserDTO userDTO = (UserDTO) request.getAttribute("user");
        response.setHeader(JWTUtils.getHeader(),  token);
        response.setContentType("application/json;charset=UTF-8");

        if (userDTO.getRole() == 0)
            return Result.sucess(userService.getUserById(userDTO.getId()));

        if (userDTO.getRole() == 1)
            return Result.sucess(cinemaService.getCinemaById(userDTO.getId()));

        return Result.sucess(userService.getAdmin());
    }

    @PostMapping("/common/register")
    public Result register(HttpServletResponse response, @RequestBody User user){
        if (!StringUtils.hasLength(user.getUserEmail())){
            return Result.failure(HttpStatusCode.BAD_REQUEST, "User email can not be null");
        }

        if (userService.exists(user.getUserEmail())){
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
        if (userService.exists(user.getUserEmail())){
            return Result.failure(HttpStatusCode.USER_ALREADY_EXISTS);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getUserEmail());
        map.put("password", user.getPassword());
        map.put("username", user.getUserName());
        map.put("location", user.getLocation());
        map.put("description", user.getDescription());
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

    @PostMapping("/common/confirm/cinema/{code}")
    public Result confirmRegisterCinema(HttpServletRequest request, @PathVariable("code") String code){
        String token = request.getHeader("registerToken");
        if (token == null){
            return Result.failure(HttpStatusCode.ACCESS_DENIED);
        }

        Claims tokenBody = JWTUtils.getTokenBody(token);
        String verifycode = tokenBody.get("verifycode").toString();
        if (!verifycode.equals(code.trim())){
            return Result.failure(HttpStatusCode.BAD_REQUEST, "Verify code does not matched!", null);
        }

        RegisterRequest r = new RegisterRequest();
        r.setAccepted(false);
        r.setCreated(LocalDateTime.now());
        r.setDescription((String) tokenBody.get("description"));
        r.setUserEmail((String) tokenBody.get("email"));
        r.setUserName((String) tokenBody.get("username"));
        r.setPassword((String) tokenBody.get("password"));
        r.setLocation((String) tokenBody.get("location"));

        if (registerRequestService.exists(r.getUserEmail()))
            return Result.failure(HttpStatusCode.BAD_REQUEST, "You have already sended a request");

        registerRequestService.save(r);

        return Result.sucess(HttpStatusCode.OK, "Waiting for admin's review. We will notify you by email when the review is over.");
    }

    @PutMapping("/user/change/password")
    public Result changePassword(HttpServletRequest request){
        String newPassw = request.getParameter("password");
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));
        User user = userService.changePasswd(userId, newPassw);
        if (user == null)
            return Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND);

        return Result.sucess("");
    }

    @PutMapping("/user/change/avatar")
    public Result change(@RequestBody MultipartFile file, HttpServletRequest request){
        int userId = JWTUtils.getUserId(request.getHeader(JWTUtils.getHeader()));
        User user = userService.changeAvatar(userId, file);
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

    @PostMapping("/user/add/favourite/film/{filmId}")
    public Result addFavouriteFilm(@PathVariable("filmId") String filmId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        boolean result = userService.addFavouriteFilm(JWTUtils.getUserId(token), filmId);

        return  result ?
                Result.sucess("")
                :
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "The film couldn't be found!");
    }

    @PostMapping("/user/add/favourite/cinema/{cinemaId}")
    public Result addFavouriteCinema(@PathVariable("cinemaId") int cinema, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        boolean result = userService.addFavouriteCinema(JWTUtils.getUserId(token), cinema);

        return  result ?
                Result.sucess("")
                :
                Result.failure(HttpStatusCode.BAD_REQUEST, cinema + " is not a cinema!");
    }

    @DeleteMapping("/user/remove/favourite/film/{filmId}")
    public Result removeFavouriteFilm(@PathVariable("filmId") String filmId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        boolean result = userService.removeFavouriteFilm(JWTUtils.getUserId(token), filmId);

        return  result ?
                Result.sucess("")
                :
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "The film couldn't be found!");

    }

    @DeleteMapping("/user/remove/favourite/cinema/{cinemaId}")
    public Result removeFavouriteCinema(@PathVariable("cinemaId") int cinema, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        boolean result = userService.removeFavouriteCinema(JWTUtils.getUserId(token), cinema);

        return  result ?
                Result.sucess("")
                :
                Result.failure(HttpStatusCode.BAD_REQUEST, cinema + " is not a cinema!");

    }

    @PostMapping("/user/buy/ticket")
    public Result buyTicket(@RequestBody Ticket ticket, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int id = JWTUtils.getUserId(token);
        String userEmail = JWTUtils.getUserEmail(token);

        PaymentDTO paymentDTO = paymentService.buyTicket(ticket, id);

        if (paymentDTO == null){
            return Result.failure(HttpStatusCode.BAD_REQUEST, "The ticket has already been sold");
        }


        Map<String, Object> map = new HashMap<>();
        map.put("email", userEmail);
        map.put("payment", JSONUtils.toJSONString(paymentDTO));
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.PAYMENT_ROUTING_KEY, map);

        return  Result.sucess("");
    }


    @GetMapping("/admin/requests")
    public Result getRequests(@RequestParam(value = "page", defaultValue = "1") int page, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int id = JWTUtils.getUserId(token);
        if (id != -1)
            return Result.failure(HttpStatusCode.ACCESS_DENIED);

        return Result.sucess(registerRequestService.getRegisters(page - 1));
    }

    @GetMapping("/admin/requests/{requestId}")
    public Result getRequest(@PathVariable("requestId") int requestId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int id = JWTUtils.getUserId(token);
        if (id != -1)
            return Result.failure(HttpStatusCode.ACCESS_DENIED);

        RegisterRequest registerRequest = registerRequestService.getRequestById(id);

        return  registerRequest == null ?
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "Request couldn't be find")
                :
                Result.sucess(registerRequest);
    }


    @PutMapping("/admin/requests/accepted/{requestId}")
    public Result acceptedRequest(@PathVariable("requestId") int requestId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int id = JWTUtils.getUserId(token);
        if (id != -1)
            return Result.failure(HttpStatusCode.ACCESS_DENIED);

        boolean accepted = registerRequestService.accept(id);

        if (accepted){
            RegisterRequest registerRequest = registerRequestService.getRequestById(id);
            User user = new User();
            user.setRole(1);
            BeanUtils.copyProperties(registerRequest, user);

            User register = userService.register(user);
            if (register == null) {
                return Result.failure(HttpStatusCode.USER_ALREADY_EXISTS);
            }

            Cinema cinema = new Cinema();
            BeanUtils.copyProperties(registerRequest, cinema);
            cinemaService.save(cinema);

            Map<String, Object> map = new HashMap<>();
            map.put("request", JSONUtils.toJSONString(registerRequest));

            rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.REQUEST_QUEUE, map);

            return Result.sucess("Accepted request!");
        }

        return  Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "Request couldn't be find");
    }

    @PutMapping("/admin/requests/refuse/{requestId}")
    public Result refuseRequest(@PathVariable("requestId") int requestId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int id = JWTUtils.getUserId(token);
        if (id != -1)
            return Result.failure(HttpStatusCode.ACCESS_DENIED);

        boolean refused = registerRequestService.refuse(id);
        RegisterRequest registerRequest = registerRequestService.getRequestById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("request", JSONUtils.toJSONString(registerRequest));

        if (refused){
            rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, RabbitMQConfig.REQUEST_QUEUE, map);
            return Result.sucess("Refused request!");
        }

        return  Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "Request couldn't be find");
    }

    @GetMapping("/user/favourite/films")
    public Result getUserFavouriteFilms(@RequestParam(value = "page", defaultValue = "1") int page, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int id = JWTUtils.getUserId(token);

        return Result.sucess(userService.getFavouriteFilmByUser(id, page - 1));
    }

    @GetMapping("/user/favourite/cinema")
    public Result getUserFavouriteCinema(@RequestParam(value = "page", defaultValue = "1") int page, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int id = JWTUtils.getUserId(token);

        return Result.sucess(userService.getFavouriteFilmByUser(id, page - 1));
    }
}
