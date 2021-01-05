package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.PremierDTO;
import ies.project.toSeeOrNot.entity.Premier;
import ies.project.toSeeOrNot.repository.PremierRepository;
import ies.project.toSeeOrNot.service.FilmService;
import ies.project.toSeeOrNot.service.PremierService;
import ies.project.toSeeOrNot.service.RoomService;
import ies.project.toSeeOrNot.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wei
 * @date 2020/12/31 20:34
 */
@Service
public class PremierServiceImpl implements PremierService {

    @Autowired
    PremierRepository premierRepository;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    FilmService filmService;

    @Override
    public PremierDTO getPremierById(int id) {
        Premier premier = premierRepository.getPremierById(id);
        PremierDTO premierDTO = new PremierDTO();
        BeanUtils.copyProperties(premier, premierDTO);
        premierDTO.setFilm(filmService.getFilmById(premier.getFilm(), false));
        premierDTO.setSchedules(scheduleService.getSchedulesByPremier(id));
        return premierDTO;
    }

    @Override
    public Premier save(Premier premier) {
        return premierRepository.save(premier);
    }
}
