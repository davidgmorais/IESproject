package ies.project.toSeeOrNot.service.impl;
import ies.project.toSeeOrNot.dto.*;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.entity.JwtUser;
import ies.project.toSeeOrNot.entity.Notification;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.exception.FilmNotFoundException;
import ies.project.toSeeOrNot.exception.UserNotFoundException;
import ies.project.toSeeOrNot.repository.FilmRepository;
import ies.project.toSeeOrNot.repository.NotificationRepository;
import ies.project.toSeeOrNot.repository.UserRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/1 16:35
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final Path root = Paths.get("uploads");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RegisterRequestService registerRequestService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private CinemaService cinemaService;

    /**
     * in our system, user's identifier is his email. Not username
     * @param email user's email
     * @return Spring security UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserEmail(email);
        if (user != null)
            return new JwtUser(user);
        throw new UserNotFoundException();
    }

    /**
     *  register a new user.
     * @param user user to be registered
     * @return if user already exist, return null
     */
    @Override
    public synchronized User register(User user){
        User userByUserEmail = userRepository.getUserByUserEmail(user.getUserEmail());
        if (userByUserEmail == null){
            //encrypt user's password
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            return userRepository.save(user);
        }

        return null;
    }

    /**
     * change user's password
     * @param id user id
     * @param newPass new password
     * @return
     */
    @Override
    public User changePasswd(int id, String newPass) {
        User user = userRepository.findUserById(id);

        if (user != null){
            user.setPassword(DigestUtils.md5DigestAsHex(newPass.getBytes()));
            userRepository.save(user);
            return user;
        }
        throw new UserNotFoundException();
    }
    /**
     * change user's avatar
     * @param id user id
     * @return
     */
    @Override
    public User changeAvatar(int id, MultipartFile file) {
        User user = userRepository.findUserById(id);
        Path newPath = this.root.resolve((user.getId() + ".jpg"));
        try {
            Files.copy(file.getInputStream(), newPath);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        user.setAvatar(newPath.toString());
        userRepository.save(user);
        return user;
    }
    @Override
    public Set<NotificationDTO> notifications(int id, Pageable page) {
        return notificationService.getNotificationsByUserId(id, page);
    }

    @Override
    public boolean addFavouriteFilm(int userId, String filmId) {
        FilmDTO film = filmService.getFilmById(filmId, false);
        if (film == null)
            return false;
        userRepository.addFavouriteFilm(userId, filmId);
        filmService.like(filmId);
        return true;
    }

    @Override
    public boolean addFavouriteCinema(int userId, int cinema) {
        if (!isCinema(cinema))
            return false;

        userRepository.addFavouriteCinema(userId, cinema);
        cinemaService.follow(cinema);
        return true;
    }

    @Override
    public boolean removeFavouriteFilm(int userId, String filmId) {
        FilmDTO film = filmService.getFilmById(filmId, false);
        if (film == null)
            return false;
        userRepository.removeFavouriteFilm(userId, filmId);
        filmService.dislike(filmId);
        return true;
    }

    @Override
    public boolean removeFavouriteCinema(int userId, int cinema) {
        if (!isCinema(cinema))
            return false;

        userRepository.removeFavouriteCinema(userId, cinema);
        cinemaService.disfollow(cinema);
        return true;
    }

    @Override
    public UserDTO getUserById(int userId) {
        User userById = userRepository.findUserById(userId);
        if (userById == null)
            return null;

        UserDTO user = new UserDTO();
        BeanUtils.copyProperties(userById, user);
        user.setNotifications(notificationService.getNumberOfNotificationsUnreadByUser(userId));
        return user;
    }

    @Override
    public boolean exists(String email) {
        User result = userRepository.getUserByUserEmail(email);
        return result != null;
    }

    @Override
    public boolean isCinema(int id) {
        User userById = userRepository.findUserById(id);

        if (userById == null)
            return false;

        return userById.getRole() == 1;
    }

    @Override
    public Set<User> getFollowedUsersByCinema(int cinema) {
        return userRepository.getUsersByCinema(cinema);
    }

    @Override
    public AdminDTO getAdmin() {
        UserDTO admin = getUserById(-1);

        AdminDTO adminDTO = new AdminDTO();
        BeanUtils.copyProperties(admin, adminDTO);
        adminDTO.setRequests(registerRequestService.getNumberOfRequestsNotProcessed());
        return adminDTO;
    }

    @Override
    public PageDTO<FilmDTO> getFavouriteFilmByUser(int user, int page) {
        return filmService.getFavouriteFilmByUser(user, page);
    }

}
