package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.PremierDTO;
import ies.project.toSeeOrNot.entity.Premier;

/**
 * @author Wei
 * @date 2020/12/31 20:32
 */
public interface PremierService {
    PremierDTO getPremierById(int id);

    Premier save(Premier premier);
}
