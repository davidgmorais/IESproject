package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.dto.GenreDTO;
import ies.project.toSeeOrNot.dto.PageDTO;
import ies.project.toSeeOrNot.dto.PremierDTO;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.entity.FilmByGenre;
import ies.project.toSeeOrNot.repository.GenreRepository;
import ies.project.toSeeOrNot.service.FilmService;
import ies.project.toSeeOrNot.service.PremierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/10 9:14
 */
@RestController
@Transactional
public class FilmController {
    private final static Integer limit = 10;

    @Autowired
    @Qualifier("filmServiceImpl")
    FilmService filmService;

    @Autowired
    PremierService premierService;

    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/common/film/title/{title}")
    public Result getMoviesByTitle(@PathVariable("title") String title, @RequestParam(value = "page", defaultValue = "1") int page){
        return Result.sucess(filmService.getFilmsByTitle(title, PageRequest.of(page - 1, limit)));
    }

    @GetMapping("/common/film/actor/{actor}")
    public Result getFilmsByActor(@PathVariable(value = "actor") String actor, @RequestParam(value = "page", defaultValue = "1") int page){
        return Result.sucess(filmService.getFilmsByActorName(actor, PageRequest.of(page - 1, limit)));
    }

    @GetMapping("/common/film/popular")
    public Result getPopularFilms(@RequestParam(value = "page", defaultValue = "1") int page){
        return Result.sucess(filmService.getFilmsSortedBy(PageRequest.of(page - 1, limit, Sort.by("rating").descending())));
    }

    @GetMapping("/common/film/recent")
    public Result getRecentFilms(@RequestParam(value = "page", defaultValue = "1") int page){
        return Result.sucess(filmService.getFilmsSortedBy(PageRequest.of(page - 1, limit, Sort.by("released").descending())));
    }

    @GetMapping("/common/film/{filmId}")
    public Result getFilmById(@PathVariable("filmId") String filmId){
        FilmDTO filmById = filmService.getFilmById(filmId, true, true);

        return filmById == null ?
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND)
                :
                Result.sucess(filmById);
    }

    @GetMapping("/common/film/genre/{genre}")
    public Result getFilmsByGenre(@PathVariable(value = "genre") String genre, @RequestParam(value = "page", defaultValue = "1") int page){
        PageDTO<FilmDTO> filmsByGenre = filmService.getFilmsByGenre(genre, PageRequest.of(page - 1, limit));
        return filmsByGenre.getData() == null ?
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "Couldn't find fimls of " + genre)
                :
                Result.sucess(filmsByGenre);
    }

    @GetMapping("/common/film/director/{director}")
    public Result getFilmsByDirector(@PathVariable(value = "director") String director, @RequestParam(value = "page", defaultValue = "1") int page){
        PageDTO<FilmDTO> filmsByDirector = filmService.getFilmsByDirector(director, PageRequest.of(page - 1, limit));
        return filmsByDirector.getData() == null ?
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "Couldn't find fimls of " + director)
                :
                Result.sucess(filmsByDirector);
    }

    @GetMapping("/common/film/year/{year}")
    public Result getFilmsByYear(@PathVariable(value = "year") int year, @RequestParam(value = "page", defaultValue = "1") int page){
        PageDTO<FilmDTO> filmsByYear = filmService.getFilmsByYear(year, PageRequest.of(page - 1, limit));
        return filmsByYear.getData() == null ?
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "Couldn't find fimls of year " + year)
                :
                Result.sucess(filmsByYear);
    }

    @GetMapping("/common/film/{filmId}/premiers{page}")
    public Result getPremiersByFilm(@PathVariable(value = "filmId") String filmId, @RequestParam(value = "page", defaultValue = "1") int page){
        PageDTO<PremierDTO> premiers = premierService.getPremiersByFilm(filmId, page - 1);
        return premiers.getData() == null ?
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "No premiers found in page " + page)
                :
                Result.sucess(premiers);
    }

    @GetMapping("/common/genres")
    public Result getGenres(){
        Iterable<FilmByGenre> all = genreRepository.findAll();
        Set<GenreDTO> genres = new HashSet<>();
        all.forEach(genre -> {
            genres.add(new GenreDTO(genre.getGenreName()));
        });
        return genres.size() == 0 ?
                Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, "No genres found")
                :
                Result.sucess(genres);
    }

    @PostMapping("/admin/add/film")
    public Result addFilm(@RequestBody Film film){
        filmService.addFilm(film);
        return Result.sucess("");
    }
}
