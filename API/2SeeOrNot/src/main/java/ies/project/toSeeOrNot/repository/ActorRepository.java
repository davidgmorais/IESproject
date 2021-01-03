package ies.project.toSeeOrNot.repository;
import ies.project.toSeeOrNot.entity.StarredIn;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/10 14:29
 */
public interface ActorRepository extends PagingAndSortingRepository<StarredIn, String> {
    Set<StarredIn> getActorsByFilm(String filmId);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO actor VALUES(:actor)")
    void saveActor(String actor);

    @Query(nativeQuery = true, value = "SELECT count(1) FROM actor WHERE actor_name = :actor")
    int getActor(String actor);
}
