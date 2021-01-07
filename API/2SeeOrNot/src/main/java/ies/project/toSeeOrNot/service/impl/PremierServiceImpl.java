package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.PageDTO;
import ies.project.toSeeOrNot.dto.PremierDTO;
import ies.project.toSeeOrNot.dto.ScheduleDTO;
import ies.project.toSeeOrNot.entity.Premier;
import ies.project.toSeeOrNot.repository.PremierRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public PremierDTO getPremierById(int id) {
        Premier premier = premierRepository.getPremierById(id);
        PremierDTO premierDTO = new PremierDTO();
        BeanUtils.copyProperties(premier, premierDTO);
        premierDTO.setFilm(filmService.getFilmById(premier.getFilm(), false));
        premierDTO.setSchedules(scheduleService.getSchedulesByPremier(id));
        return premierDTO;
    }

    public PremierDTO updateCache(ScheduleDTO scheduleDTO, int premier){
        PremierDTO premierById = getPremierById(premier);
        premierById.getSchedules().add(scheduleDTO);
        return premierById;
    }

    @Override
    public PageDTO<PremierDTO> getPremiersByCinema(int cinema, int page) {
        Page<Premier> premiers = premierRepository.getPremierByCinema(cinema, PageRequest.of(page, 10, Sort.by("start").descending()));

        Set<PremierDTO> collect = premiers.getContent().stream().map(premier -> {
            PremierDTO premierDTO = new PremierDTO();
            BeanUtils.copyProperties(premier, premierDTO);
            premierDTO.setFilm(filmService.getFilmById(premier.getFilm(), false));
            premierDTO.setSchedules(scheduleService.getSchedulesByPremier(premier.getId()));
            return premierDTO;
        }).collect(Collectors.toSet());

        return new PageDTO<>(collect, premiers.getTotalPages(), premiers.getTotalElements());
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
