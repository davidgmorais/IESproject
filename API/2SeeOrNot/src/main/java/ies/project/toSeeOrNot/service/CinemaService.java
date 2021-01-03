package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.CinemaDTO;
import org.springframework.stereotype.Service;

/**
 * @author Wei
 * @date 2020/12/31 17:48
 */
public interface CinemaService {
    CinemaDTO getCinemaById(int id);
}
