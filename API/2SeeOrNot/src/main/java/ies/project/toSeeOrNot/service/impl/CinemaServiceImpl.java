package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.CinemaDTO;
import ies.project.toSeeOrNot.entity.Cinema;
import ies.project.toSeeOrNot.repository.CinemaRepository;
import ies.project.toSeeOrNot.service.CinemaService;
import ies.project.toSeeOrNot.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Wei
 * @date 2020/12/31 17:48
 */
@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    UserService userService;

    @Override
    public CinemaDTO getCinemaById(int id) {
        Cinema cinema = cinemaRepository.getCinemaById(id);

        if (cinema == null)
            return null;

        CinemaDTO cinemaDTO = new CinemaDTO();
        BeanUtils.copyProperties(cinema, cinemaDTO);
        cinemaDTO.setUser(userService.getUserById(cinema.getId()));
        return cinemaDTO;
    }
}
