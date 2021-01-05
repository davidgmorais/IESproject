package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.entity.StarredIn;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/10 15:46
 */
public interface ActorService {
    Set<StarredIn> getActorsByFilmId(String film);
}
