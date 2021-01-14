package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.component.RedisUtils;
import ies.project.toSeeOrNot.entity.StarredIn;
import ies.project.toSeeOrNot.repository.ActorRepository;
import ies.project.toSeeOrNot.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/10 15:47
 */
@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public Set<StarredIn> getActorsByFilmId(String film) {
        Set<StarredIn> list = (Set<StarredIn>) redisUtils.getSet("actors:" + film);
        if (list != null)
            return list;

        Set<StarredIn> result = actorRepository.getActorsByFilm(film);
        redisUtils.storeSet("actors:" + film, result);
        return result;
    }

}
