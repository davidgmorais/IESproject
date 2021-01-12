package ies.project.toSeeOrNot.repository;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

/**
 * @author Wei
 * @date 02/12/2020 16:49
 */
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User getUserByUserEmail(String email);

    User findUserById(int id);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO favouritefilm VALUES(:user,:film)")
    void addFavouriteFilm(int user, String film);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM favouritefilm WHERE user = :user and film = :filmId")
    void removeFavouriteFilm(int user, String filmId);

    @Query(value = "SELECT new User(u.id, u.userName, u.userEmail, u.password, u.flag, u.avatar, u.role) FROM User u LEFT JOIN FavouriteCinema c on u.id = c.user WHERE c.cinema = :cinema")
    Set<User> getUsersByCinema(int cinema);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO favouritecinema VALUES(:user,:cinema)")
    void addFavouriteCinema(int user, int cinema);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM favouritecinema WHERE user = :user and cinema = :cinema)")
    void removeFavouriteCinema(int user, int cinema);


}