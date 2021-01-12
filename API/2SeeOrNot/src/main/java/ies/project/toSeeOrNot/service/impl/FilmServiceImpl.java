package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.component.RedisUtils;
import ies.project.toSeeOrNot.dto.*;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @Autowired
    RedisUtils redisUtils;
    
    @Override
    public PageDTO<FilmDTO> getFilmsByTitle(String title, Pageable pageable) {
        PageDTO<FilmDTO> cache = (PageDTO<FilmDTO>) redisUtils.get("films:title:" + title + ":" + pageable.getPageNumber());
        if (cache != null){
            return cache;
        }
        
        Page<Film> filmsByTitle = filmRepository.getFilmsByTitleStartsWith(title, pageable);
        Set<FilmDTO> filmDTOS = fillList(filmsByTitle.getContent());
        PageDTO<FilmDTO> filmDTOPageDTO = new PageDTO<>(filmDTOS, filmsByTitle.getTotalPages(), filmsByTitle.getTotalElements());
        redisUtils.add("films:title:" + title + ":" + pageable.getPageNumber(), filmDTOPageDTO);
        return filmDTOPageDTO;
    }

    @Override
    public PageDTO<FilmDTO> getFilmsByActorName(String actorName, Pageable pageable) {
        PageDTO<FilmDTO> cache = (PageDTO<FilmDTO>) redisUtils.get("films:actor:" + actorName + ":" + pageable.getPageNumber());
        if (cache != null){
            return cache;
        }
        Page<Film> filmsByActor = filmRepository.getFilmsByActor(actorName, pageable);
        Set<FilmDTO> filmDTOS = fillList(filmsByActor.getContent());
        PageDTO<FilmDTO> filmDTOPageDTO = new PageDTO<>(filmDTOS, filmsByActor.getTotalPages(), filmsByActor.getTotalElements());
        redisUtils.add("films:actor:" + actorName + ":" + pageable.getPageNumber(), filmDTOPageDTO);
        return filmDTOPageDTO;
    }

    @Override
    public PageDTO<FilmDTO> getFilmsSortedBy(Pageable pageable) {
        PageDTO<FilmDTO> cache = (PageDTO<FilmDTO>) redisUtils.get("films:sorted:" + pageable.getSort().toString() + ":" + pageable.getPageNumber());
        if (cache != null)
            return cache;
        
        Page<Film> all = filmRepository.findAll(pageable);
        Set<FilmDTO> filmDTOS = fillList(all.getContent());
        PageDTO<FilmDTO> filmDTOPageDTO = new PageDTO<>(filmDTOS, all.getTotalPages(), all.getTotalElements());
        
        redisUtils.add("films:sorted:"+pageable.getSort().toString()+":"+pageable.getPageNumber(), filmDTOPageDTO);
        return filmDTOPageDTO;
    }

    /**
     * get a film with movie_id @param {filmId}
     * @param filmId movie_id
     * @return filmDTO
     */
    @Override
    public FilmDTO getFilmById(String filmId, boolean wantComments) {
        Film film = filmRepository.getFilmByMovieId(filmId);
        if (film == null)
            return null;

        Set<FilmDTO> filmDTOS = fillList(List.of(film));

        FilmDTO filmDTO = filmDTOS.iterator().next();
        if (wantComments){
            PageDTO<CommentDTO> commentsByFilm = commentService.getCommentsByFilm(filmDTO.getMovieId(), 0);
            filmDTO.setComments(commentsByFilm);
            filmDTO.setCommentPages((int) Math.ceil(commentService.getNumberOfCommentsByFilm(filmId) / 15.0));
        }
        return filmDTO;
    }

    @Override
    public PageDTO<FilmDTO> getFilmsByGenre(String genre, Pageable page) {
        PageDTO<FilmDTO> cache = (PageDTO<FilmDTO>) redisUtils.get("films:genre:" + genre + ":" + page.getPageNumber());
        if (cache != null){
            return cache;
        }
        Page<Film> filmsByGenre = filmRepository.getFilmsByGenre(genre, page);

        if (filmsByGenre.getContent().size() == 0)
            return null;

        Set<FilmDTO> filmDTOS = fillList(filmsByGenre.getContent());
        PageDTO<FilmDTO> filmDTOPageDTO = new PageDTO<>(filmDTOS, filmsByGenre.getTotalPages(), filmsByGenre.getTotalElements());

        redisUtils.add("films:genre:" + genre + ":" + page.getPageNumber(), filmDTOPageDTO);
        return filmDTOPageDTO;
    }

    @Override
    public PageDTO<FilmDTO> getFilmsByDirector(String director, Pageable page) {
        PageDTO<FilmDTO> cache = (PageDTO<FilmDTO>) redisUtils.get("films:director:" + director + ":" + page.getPageNumber());
        if (cache != null){
            return cache;
        }

        Page<Film> filmsByDirector = filmRepository.getFilmsByDirector(director, page);
        Set<FilmDTO> filmDTOS = fillList(filmsByDirector.getContent());
        PageDTO<FilmDTO> filmDTOPageDTO = new PageDTO<>(filmDTOS, filmsByDirector.getTotalPages(), filmsByDirector.getTotalElements());

        redisUtils.add("films:director:" + director + ":" + page.getPageNumber(), filmDTOPageDTO);
        return filmDTOPageDTO;
    }

    @Override
    public PageDTO<FilmDTO> getFilmsByYear(int year, Pageable page) {
        PageDTO<FilmDTO> cache = (PageDTO<FilmDTO>) redisUtils.get("films:year:" + year + ":" + page.getPageNumber());

        if (cache != null){
            return cache;
        }

        Page<Film> filmsByYear = filmRepository.getFilmsByYearAfterAndYearBefore(year - 1, year + 1, page);
        Set<FilmDTO> filmDTOS = fillList(filmsByYear.getContent());
        PageDTO<FilmDTO> filmDTOPageDTO = new PageDTO<>(filmDTOS, filmsByYear.getTotalPages(), filmsByYear.getTotalElements());

        redisUtils.add("films:year:" + year + ":" + page.getPageNumber(), filmDTOPageDTO);
        return filmDTOPageDTO;
    }

    @Override
    public PageDTO<FilmDTO> getFavouriteFilmByUser(int user, int page) {
        Page<Film> favouriteFilms = filmRepository.getFavouriteFilms(user, PageRequest.of(page, 10));
        Set<FilmDTO> filmDTOS = fillList(favouriteFilms.getContent());
        return new PageDTO<>(filmDTOS, favouriteFilms.getTotalPages(), favouriteFilms.getTotalElements());
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

    @Override
    public synchronized void like(String film) {
        filmRepository.like(film);
    }

    @Override
    public synchronized void dislike(String film) {
        filmRepository.dislike(film);
    }

    private Set<FilmDTO> fillList(List<Film> films){
        if (films.size() == 0){
            return null;
        }

        Set<FilmDTO> filmDTOS = new HashSet<>();

        films.forEach(film -> {
            FilmDTO filmDTO = new FilmDTO();
            BeanUtils.copyProperties(film, filmDTO);

            //get list of actors
            Set<StarredIn> actorsByFilm = actorRepository.getActorsByFilm(film.getMovieId());
            Set<ActorDTO> collect = actorsByFilm.stream().map(
                    starredIn -> new ActorDTO(starredIn.getActor()))
                    .collect(Collectors.toSet());
            filmDTO.setActors(collect);

            //get types
            Set<FilmByGenre> genresByFilm = genreRepository.getGenresByFilm(film.getMovieId());
            Set<GenreDTO> genres = genresByFilm.stream().map(
                    filmByGenre -> new GenreDTO(filmByGenre.getGenreName())
            ).collect(Collectors.toSet());
            filmDTO.setGenres(genres);

            filmDTOS.add(filmDTO);
        });

        return filmDTOS;
    }
}
