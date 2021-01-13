package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.FilmDTO;
import ies.project.toSeeOrNot.dto.PageDTO;
import ies.project.toSeeOrNot.dto.PremierDTO;
import ies.project.toSeeOrNot.entity.Premier;
import org.springframework.data.domain.Page;

import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/31 20:32
 */
public interface PremierService {
    PremierDTO getPremierById(int id);

    PageDTO<PremierDTO> getPremiersByCinema(int cinema, int page);

    PageDTO<PremierDTO> getPremiersByFilm(String film, int page);

    Premier createPremier(Premier premier);

    boolean delete(int premier);
}
