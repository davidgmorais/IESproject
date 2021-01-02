package ies.project.toSeeOrNot.repository;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Wei
 * @date 02/12/2020 16:49
 */
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User getUserByUserEmail(String email);
    User findUserById(int id);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO favouritefilm VALUES(:userId,:film)")
    void addFavouriteFilm(int userId, String film);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM favouritefilm WHERE user = :userId and film = :filmId")
    void removeFavouriteFilm(int userId, String filmId);

}