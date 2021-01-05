package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.PremierDTO;
import ies.project.toSeeOrNot.entity.Premier;

import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/31 20:32
 */
public interface PremierService {
    PremierDTO getPremierById(int id);

    Set<PremierDTO> getPremiersByCinema(int cinema, int page);

    Premier createPremier(Premier premier);

    boolean delete(int premier);
}
