package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.PremierDTO;
import ies.project.toSeeOrNot.dto.ScheduleDTO;
import ies.project.toSeeOrNot.entity.Premier;
import ies.project.toSeeOrNot.repository.PremierRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    TicketService ticketService;

    @Override
    @Cacheable(value = "premier", key = "#root.methodName+'['+#id+']'", unless = "#result == null")
    public PremierDTO getPremierById(int id) {
        Premier premier = premierRepository.getPremierById(id);
        PremierDTO premierDTO = new PremierDTO();
        BeanUtils.copyProperties(premier, premierDTO);
        premierDTO.setFilm(filmService.getFilmById(premier.getFilm(), false));
        premierDTO.setSchedules(scheduleService.getSchedulesByPremier(id));
        return premierDTO;
    }

    @Override
    @Cacheable(value = "premier", key = "#root.methodName+'['+#cinema+'_'+#page+']'", unless = "#result == null")
    public Set<PremierDTO> getPremiersByCinema(int cinema, int page) {
        Page<Premier> premiers = premierRepository.getPremierByCinema(cinema, PageRequest.of(page, 10, Sort.by("start").descending()));

        return premiers.getContent().stream().map(premier -> {
                    PremierDTO premierDTO = new PremierDTO();
                    BeanUtils.copyProperties(premier, premierDTO);
                    premierDTO.setFilm(filmService.getFilmById(premier.getFilm(), false));
                    premierDTO.setSchedules(scheduleService.getSchedulesByPremier(premier.getId()));
                    return premierDTO;
                }).collect(Collectors.toSet());
    }

    @Override
    public Premier createPremier(Premier premier) {
        Premier save = premierRepository.save(premier);
        ticketService.createTickets(save);
        return save;
    }

    @Override
    public boolean delete(int premier) {
        Set<ScheduleDTO> schedules = scheduleService.getSchedulesByPremier(premier);
        for (ScheduleDTO schedule : schedules){
            if (schedule.getSolds() != 0){
                return false;
            }
        }

        schedules.forEach(schedule -> {
            scheduleService.delete(schedule.getId());
        });
        return true;
    }
}
