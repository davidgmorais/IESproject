package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.NotificationDTO;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
    User changePasswd(Integer id, String newPass);

    /**
     * return all notifications of user
     * @param id user id
     * @return list of notifications
     */
    List<NotificationDTO> notifications(Integer id, Pageable page);

    void addFavouriteFilm(Integer userId, String fimId);

    void removeFavouriteFilm(Integer userid, String filmId);
}
