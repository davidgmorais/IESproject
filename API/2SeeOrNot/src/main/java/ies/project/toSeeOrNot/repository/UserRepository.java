package ies.project.toSeeOrNot.repository;
import ies.project.toSeeOrNot.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * @author Wei
 * @date 02/12/2020 16:49
 */
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User getUserByUserEmail(String email);
    User findUserById(Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO favourite VALUES(:userId,:film)")
    void addFavouriteFilm(Integer userId, String film);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM favourite WHERE user = :userId and film = :filmId")
    void removeFavouriteFilm(Integer userId, String filmId);
}