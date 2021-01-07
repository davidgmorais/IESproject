package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.entity.StarredIn;
import ies.project.toSeeOrNot.repository.ActorRepository;
import ies.project.toSeeOrNot.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/10 15:47
 */
@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Override
    public Set<StarredIn> getActorsByFilmId(String film) {
        return actorRepository.getActorsByFilm(film);
    }

}
