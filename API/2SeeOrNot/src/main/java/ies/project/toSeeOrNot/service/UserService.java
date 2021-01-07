package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.AdminDTO;
import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.dto.NotificationDTO;
import ies.project.toSeeOrNot.dto.UserDTO;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/10 9:28
 */
public interface UserService {
    User register(User user);

    /**
     * change the password of the user with the given id
     * @param id user id
     * @param newPass   new password
     * @return user
     */
    User changePasswd(int id, String newPass);
    User changeAvatar(int id, MultipartFile file);
    /**
     * return all notifications of user
     * @param id user id
     * @return list of notifications
     */
    Set<NotificationDTO> notifications(int id, Pageable page);

    boolean addFavouriteFilm(int userId, String fimId);

    boolean removeFavouriteFilm(int userid, String filmId);

    UserDTO getUserById(int userId);

    boolean exists(String email);

    boolean isCinema(int id);

    Set<Integer> getFollowedUsersByCinema(int cinema);

    AdminDTO getAdmin();
}
