package ies.project.toSeeOrNot.repository;
import ies.project.toSeeOrNot.entity.Actor;
import ies.project.toSeeOrNot.entity.Film;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Wei
 * @date 2020/12/10 14:29
 */
public interface ActorRepository extends PagingAndSortingRepository<Actor, String> {
    List<Actor> getActorsByFilm(String filmId);
}
