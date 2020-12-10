package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.entity.Actor;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.repository.ActorRepository;
import ies.project.toSeeOrNot.repository.FilmRepository;
import ies.project.toSeeOrNot.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wei
 * @date 2020/12/10 9:14
 */
@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    FilmRepository repository;

    @Autowired
    ActorRepository actorRepository;

    @Override
    public List<Film> getFilmByTitle(String title, Pageable pageable) {
        List<Film> filmsByTitleStartsWith = repository.getFilmsByTitleStartsWith(title, pageable);

      /*  List<FilmDTO> filmDTOS = new ArrayList<>();
        List<Actor> actorsByFilm = actorRepository.getActorsByFilm(filmDTOS.get(0).getMovieId());

        for (Film film : filmsByTitleStartsWith){
            FilmDTO filmDTO = new FilmDTO();
        }
*/
        return null;
    }
}
