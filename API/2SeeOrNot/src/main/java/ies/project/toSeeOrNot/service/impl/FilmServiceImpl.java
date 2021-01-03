package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.ActorDTO;
import ies.project.toSeeOrNot.dto.CommentDTO;
import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.dto.GenreDTO;
import ies.project.toSeeOrNot.entity.FilmByCountry;
import ies.project.toSeeOrNot.entity.StarredIn;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.entity.FilmByGenre;
import ies.project.toSeeOrNot.repository.ActorRepository;
import ies.project.toSeeOrNot.repository.CountryRepository;
import ies.project.toSeeOrNot.repository.FilmRepository;
import ies.project.toSeeOrNot.repository.GenreRepository;
import ies.project.toSeeOrNot.service.CommentService;
import ies.project.toSeeOrNot.service.FilmService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CommentService commentService;

    @Override
    public List<FilmDTO> getFilmsByTitle(String title, Pageable pageable) {
        Page<Film> filmsByTitleStartsWith = filmRepository.getFilmsByTitleStartsWith(title, pageable);
        return fillList(filmsByTitleStartsWith.getContent());
    }

    @Override
    public List<FilmDTO> getFilmsByActorName(String actorName, Pageable pageable) {
        Page<Film> filmsByActor = filmRepository.getFilmsByActor(actorName, pageable);
        return fillList(filmsByActor.getContent());
    }

    @Override
    public List<FilmDTO> getFilmsSortedBy(Pageable pageable) {
        Page<Film> all = filmRepository.findAll(pageable);
        return fillList(all.getContent());
    }

    /**
     * get a film with movie_id @param {filmId}
     * @param filmId movie_id
     * @return filmDTO
     */
    @Override
    public FilmDTO getFilmById(String filmId, boolean comments) {
        Film film = filmRepository.getFilmByMovieId(filmId);
        List<FilmDTO> filmDTOS = fillList(List.of(film));

        FilmDTO filmDTO = filmDTOS.get(0);
        if (comments){
            List<CommentDTO> commentsByFilm = commentService.getCommentsByFilm(filmDTO.getMovieId(), 0);
            filmDTO.setComments(commentsByFilm);

        }
        return filmDTO;
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
    public List<FilmDTO> getFilmsByYear(int year, Pageable page) {
        Page<Film> filmsByYear = filmRepository.getFilmsByYearAfterAndYearBefore(year - 1, year + 1, page);
        return fillList(filmsByYear.getContent());
    }

    @Override
    public void addFilm(Film film) {
        filmRepository.save(film);
        film.getGenre().forEach(genre ->{
            FilmByGenre filmByGenre = new FilmByGenre();
            filmByGenre.setFilm(film.getMovieId());
            filmByGenre.setGenreName(genre);

            if (genreRepository.getGenre(genre) != 1)
                genreRepository.saveGenre(genre);

            genreRepository.save(filmByGenre);
        });

        film.getActors().forEach(actor ->{
            StarredIn starredIn = new StarredIn();
            starredIn.setFilm(film.getMovieId());
            starredIn.setActor(actor);
            starredIn.setPersonage("");
            
            if (actorRepository.getActor(actor) != 1)
                actorRepository.saveActor(actor);

            actorRepository.save(starredIn);
        });

        film.getCountry().forEach(country -> {
            FilmByCountry filmByCountry = new FilmByCountry();
            filmByCountry.setFilm(film.getMovieId());
            filmByCountry.setCountryName(country);

            if (countryRepository.getCountry(country) != 1)
                countryRepository.saveCountry(country);

            countryRepository.save(filmByCountry);
        });

    }

    private List<FilmDTO> fillList(List<Film> films){
        if (films.size() == 0){
            return new ArrayList<>();
        }

        List<FilmDTO> filmDTOS = new ArrayList<>();
        for (Film film : films){
            FilmDTO filmDTO = new FilmDTO();
            BeanUtils.copyProperties(film, filmDTO);
         //   filmDTO.setYear(film.getYear().getYear());

            //get list of actors
            List<StarredIn> actorsByFilm = actorRepository.getActorsByFilm(film.getMovieId());
            List<ActorDTO> collect = actorsByFilm.stream().map(
                    starredIn -> new ActorDTO(starredIn.getActor()))
                    .collect(Collectors.toList());
            filmDTO.setActors(collect);

            //get types
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
