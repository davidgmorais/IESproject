package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.entity.Film;
import ies.project.toSeeOrNot.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;

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
       return Result.sucess(filmService.getFilmById(filmId, true));
    }

    @GetMapping("/common/film/genre/{genre}")
    public Result getFilmsByGenre(@PathVariable(value = "genre") String genre, @RequestParam(value = "page", defaultValue = "1") int page){
        List<FilmDTO> filmsByGenre = filmService.getFilmsByGenre(genre, PageRequest.of(page - 1, limit));
        return Result.sucess(filmsByGenre);
    }

    @GetMapping("/common/film/director/{director}")
    public Result getFilmsByDirector(@PathVariable(value = "director") String director, @RequestParam(value = "page", defaultValue = "1") int page){
        List<FilmDTO> filmsByDirector = filmService.getFilmsByDirector(director, PageRequest.of(page - 1, limit));
        return Result.sucess(filmsByDirector);
    }

    @GetMapping("/common/film/year/{year}")
    public Result getFilmsByYear(@PathVariable(value = "year") int year, @RequestParam(value = "page", defaultValue = "1") int page){
        List<FilmDTO> filmsByYear = filmService.getFilmsByYear(year, PageRequest.of(page - 1, limit));
        return Result.sucess(filmsByYear);
    }

    @PostMapping("/admin/add/film")
    public Result addFilm(@RequestBody Film film){
        filmService.addFilm(film);
        return Result.sucess("");
    }
}
