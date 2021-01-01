package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.entity.StarredIn;

import java.util.List;

/**
 * @author Wei
 * @date 2020/12/10 15:46
 */
public interface ActorService {
    List<StarredIn> getActorsByFilmId(Integer id);
}
