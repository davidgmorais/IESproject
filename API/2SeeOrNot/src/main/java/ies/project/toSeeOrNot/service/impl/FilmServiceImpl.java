package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.ActorDTO;
import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.dto.GenreDTO;
import ies.project.toSeeOrNot.entity.StarredIn;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.entity.FilmByGenre;
import ies.project.toSeeOrNot.repository.ActorRepository;
import ies.project.toSeeOrNot.repository.FilmRepository;
import ies.project.toSeeOrNot.repository.GenreRepository;
import ies.project.toSeeOrNot.service.FilmService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wei
 * @date 2020/12/10 9:14
 */
@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    FilmRepository filmRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    GenreRepository genreRepository;

    @Override
    public List<FilmDTO> getFilmsByTitle(String title, Pageable pageable) {
        Page<Film> filmsByTitleStartsWith = filmRepository.getFilmsByTitleStartsWith(title, pageable);
        return fillList(filmsByTitleStartsWith.getContent());
    }

    @Override
    public List<FilmDTO> getFilmsByActorName(String actorName, Pageable pageable) {
     /*   Page<Film> filmsByActor = filmRepository.getFilmsByActor(actorName, pageable);
        return fillList(filmsByActor.getContent());*/
        return null;
    }

    @Override
    public List<FilmDTO> getFilmsSortedBy(Pageable pageable) {
        Page<Film> all = filmRepository.findAll(pageable);
        return fillList(all.getContent());
    }

    @Override
    public FilmDTO getFilmById(String filmId) {
        Film film = filmRepository.getFilmByMovieId(filmId);
        List<FilmDTO> filmDTOS = fillList(List.of(film));
        return filmDTOS.get(0);
    }

    @Override
    public List<FilmDTO> getFilmsByGenre(String genre, Pageable page) {
        Page<Film> filmsByGenre = filmRepository.getFilmsByGenre(genre, page);
        return fillList(filmsByGenre.getContent());
    }

    @Override
    public List<FilmDTO> getFilmsByDirector(String director, Pageable page) {
        Page<Film> filmsByDirector = filmRepository.getFilmsByDirector(director, page);
        return fillList(filmsByDirector.getContent());
    }

    @Override
    public List<FilmDTO> getFilmsByYear(Date year, Pageable page) {
        Page<Film> filmsByYear = filmRepository.getFilmsByYear(year, page);
        return fillList(filmsByYear.getContent());
    }

    private List<FilmDTO> fillList(List<Film> films){
        if (films.size() == 0){
            return new ArrayList<>();
        }

        List<FilmDTO> filmDTOS = new ArrayList<>();
        for (Film film : films){
            FilmDTO filmDTO = new FilmDTO();
            BeanUtils.copyProperties(film, filmDTO);

            //get list of actors
            List<StarredIn> actorsByFilm = actorRepository.getActorsByFilm(film.getMovieId());
            List<ActorDTO> collect = actorsByFilm.stream().map(
                    starredIn -> new ActorDTO(starredIn.getActor(), starredIn.getPersonage()))
                    .collect(Collectors.toList());
            filmDTO.setActors(collect);

            //get types of film
            List<FilmByGenre> genresByFilm = genreRepository.getGenresByFilm(film.getMovieId());
            List<GenreDTO> genres = genresByFilm.stream().map(
                    filmByGenre -> new GenreDTO(filmByGenre.getGenreName())
            ).collect(Collectors.toList());
            filmDTO.setGenres(genres);

            filmDTOS.add(filmDTO);
        }
        return filmDTOS;
    }
}
