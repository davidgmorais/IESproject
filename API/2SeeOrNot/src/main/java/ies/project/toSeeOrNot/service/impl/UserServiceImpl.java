package ies.project.toSeeOrNot.service.impl;
import ies.project.toSeeOrNot.dto.NotificationDTO;
import ies.project.toSeeOrNot.dto.UserDTO;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.entity.JwtUser;
import ies.project.toSeeOrNot.entity.Notification;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.exception.FilmNotFoundException;
import ies.project.toSeeOrNot.exception.UserNotFoundException;
import ies.project.toSeeOrNot.repository.FilmRepository;
import ies.project.toSeeOrNot.repository.NotificationRepository;
import ies.project.toSeeOrNot.repository.UserRepository;
import ies.project.toSeeOrNot.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wei
 * @date 2020/12/1 16:35
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private FilmRepository filmRepository;

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
    public User register(User user){
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
    public User changePasswd(Integer id, String newPass) {
        User user = userRepository.findUserById(id);

        if (user != null){
            user.setPassword(DigestUtils.md5DigestAsHex(newPass.getBytes()));
            userRepository.save(user);
            return user;
        }
        return null;
    }

    @Override
    public List<NotificationDTO> notifications(Integer id, Pageable page) {
        List<Notification> allNotificationsOfCurrentUser = notificationRepository.findAllByReceiver(id, page).getContent();

        if (allNotificationsOfCurrentUser.size() == 0){
            return new ArrayList<>();
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        User currentUser =  userRepository.findUserById(allNotificationsOfCurrentUser.get(0).getReceiver());

        for (Notification notification : allNotificationsOfCurrentUser){
            //create notificationDto
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);

            //create userDto for receiver
            UserDTO receiver = new UserDTO();
            BeanUtils.copyProperties(currentUser, receiver);
            notificationDTO.setReceiver(receiver);

            //create userDto for sender
            UserDTO sender = new UserDTO();
            BeanUtils.copyProperties(currentUser, sender);
            notificationDTO.setSender(sender);

            notificationDTOS.add(notificationDTO);
        }

        return notificationDTOS;
    }

    @Override
    public void addFavouriteFilm(Integer userId, String filmId) {
        Film film = filmRepository.getFilmByMovieId(filmId);
        if (film == null)
            throw new FilmNotFoundException();
        userRepository.addFavouriteFilm(userId, filmId);
    }

    @Override
    public void removeFavouriteFilm(Integer userId, String filmId) {
        Film film = filmRepository.getFilmByMovieId(filmId);
        if (film == null)
            throw new FilmNotFoundException();
        userRepository.removeFavouriteFilm(userId, filmId);
    }

}
