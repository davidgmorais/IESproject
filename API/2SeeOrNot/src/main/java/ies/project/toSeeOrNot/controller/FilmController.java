package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Wei
 * @date 2020/12/10 9:14
 */
@RestController
public class FilmController {
    private final static Integer limit = 10;

    @Autowired
    @Qualifier("filmServiceImpl")
    FilmService filmService;

    @GetMapping("/common/film/title")
    public Result getMoviesByTitle(@RequestParam("t") String title, @RequestParam(value = "page", defaultValue = "1") Integer page){
        return Result.sucess(filmService.getFilmsByTitle(title, PageRequest.of(page - 1, limit)));
    }

    @GetMapping("/common/film/actor")
    public Result getFilmsByActor(@RequestParam(value = "a") String actor, @RequestParam(value = "page", defaultValue = "1") Integer page){
        return Result.sucess(filmService.getFilmsByActorName(actor, PageRequest.of(page - 1, limit)));
    }

    @GetMapping("/common/film/popular")
    public Result getPopularFilms(@RequestParam(value = "page", defaultValue = "1") Integer page){
        return Result.sucess(filmService.getFilmsSortedBy(PageRequest.of(page - 1, limit, Sort.by("rating").descending())));
    }

    @GetMapping("/common/film/recent")
    public Result getRecentFilms(@RequestParam(value = "page", defaultValue = "1") Integer page){
        return Result.sucess(filmService.getFilmsSortedBy(PageRequest.of(page - 1, limit, Sort.by("released").descending())));
    }

    @GetMapping("/common/film")
    public Result getFilmById(@RequestParam("id") String id){
        return Result.sucess(filmService.getFilmById(id));
    }

    @GetMapping("/common/film/genre")
    public Result getFilmsByGenre(@RequestParam(value = "g") String genre, @RequestParam(value = "page", defaultValue = "1") Integer page){
        List<FilmDTO> filmsByGenre = filmService.getFilmsByGenre(genre, PageRequest.of(page - 1, limit));
        return Result.sucess(filmsByGenre);
    }

    @GetMapping("/common/film/director")
    public Result getFilmsByDirector(@RequestParam(value = "d") String director, @RequestParam(value = "page", defaultValue = "1") Integer page){
        List<FilmDTO> filmsByDirector = filmService.getFilmsByDirector(director, PageRequest.of(page - 1, limit));
        return Result.sucess(filmsByDirector);
    }

    @GetMapping("/common/film/year")
    public Result getFilmsByYear(@RequestParam(value = "y") Integer year, @RequestParam(value = "page", defaultValue = "1") Integer page){
        List<FilmDTO> filmsByYear = filmService.getFilmsByYear(year, PageRequest.of(page - 1, limit));
        return Result.sucess(filmsByYear);
    }
}
