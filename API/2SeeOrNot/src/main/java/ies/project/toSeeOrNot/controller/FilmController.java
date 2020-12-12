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
    public Result getMoviesByTitle(@RequestParam("name") String title, @RequestParam("page") Integer page){
        return Result.sucess(filmService.getFilmsByTitle(title, PageRequest.of(page, limit)));
    }

    @GetMapping("/common/film/actor")
    public Result getFilmsByActor(@RequestParam(value = "name") String actor, @RequestParam(value = "page", defaultValue = "0") Integer page){
        return Result.sucess(filmService.getFilmsByActorName(actor, PageRequest.of(page, limit)));
    }

    @GetMapping("/common/film/popular")
    public Result getPopularFilms(@RequestParam(value = "page", defaultValue = "0") Integer page){
        return Result.sucess(filmService.getFilmsSortedBy(PageRequest.of(page, limit, Sort.by("rating").descending())));
    }

    @GetMapping("/common/film/recent")
    public Result getRecentFilms(@RequestParam(value = "page", defaultValue = "0") Integer page){
        return Result.sucess(filmService.getFilmsSortedBy(PageRequest.of(page, limit, Sort.by("released").descending())));
    }

    @GetMapping("/common/film/id/{id}")
    public Result getFilmById(@PathVariable("id") String id, @RequestParam(value = "page", defaultValue = "0") Integer page){
        return Result.sucess(filmService.getFilmById(id));
    }

    @GetMapping("/common/film/genre/{genre}")
    public Result getFilmsByGenre(@PathVariable(value = "genre") String genre, @RequestParam(value = "page", defaultValue = "0") Integer page){
        List<FilmDTO> filmsByGenre = filmService.getFilmsByGenre(genre, PageRequest.of(page, limit));
        return Result.sucess(filmsByGenre);
    }

    @GetMapping("/common/film/director/{director}")
    public Result getFilmsByDirector(@PathVariable(value = "director") String director, @RequestParam(value = "page", defaultValue = "0") Integer page){
        List<FilmDTO> filmsByDirector = filmService.getFilmsByDirector(director, PageRequest.of(page, limit));
        return Result.sucess(filmsByDirector);
    }

    @GetMapping("/common/film/year/{year}")
    public Result getFilmsByYear(@PathVariable(value = "year") Integer year, @RequestParam(value = "page", defaultValue = "0") Integer page){
        List<FilmDTO> filmsByYear = filmService.getFilmsByYear(year, PageRequest.of(page, limit));
        return Result.sucess(filmsByYear);
    }
}
