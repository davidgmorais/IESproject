package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.repository.FilmRepository;
import ies.project.toSeeOrNot.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/common/movie")
    public Result getMoviesByTitle(@RequestParam("title") String title, @RequestParam("page") Integer page){
        filmService.getFilmByTitle(title, PageRequest.of(page, limit));
        return null;
    }
}
