package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.component.RedisUtils;
import ies.project.toSeeOrNot.dto.*;
import ies.project.toSeeOrNot.entity.Premier;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.repository.CinemaRepository;
import ies.project.toSeeOrNot.repository.PremierRepository;
import ies.project.toSeeOrNot.repository.UserRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
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

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    UserRepository userRepository;

    @Override
    public PremierDTO getPremierById(int id) {
        PremierDTO cache = (PremierDTO) redisUtils.get("premier:" + id);
        if (cache != null)
            return cache;
        Premier premier = premierRepository.getPremierById(id);
        PremierDTO premierDTO = getDTO(premier);
        redisUtils.add("premier:" + id, premierDTO);
        return premierDTO;
    }

    @Override
    public PageDTO<PremierDTO> getPremiersByCinema(int cinema, int page) {
        PageDTO<PremierDTO>  cache = (PageDTO<PremierDTO>) redisUtils.get("cinema:" + cinema + ":premiers:" + page);
        if (cache != null)
            return cache;

        Page<Premier> premiers = premierRepository.getPremierByCinema(cinema, PageRequest.of(page, 10, Sort.by("start").descending()));

        Set<PremierDTO> collect = premiers.getContent().stream().map(premier -> {
            PremierDTO premierDTO = new PremierDTO();
            BeanUtils.copyProperties(premier, premierDTO);
            premierDTO.setFilm(filmService.getFilmById(premier.getFilm(), false, false));
            premierDTO.setSchedules(scheduleService.getSchedulesByPremier(premier.getId()));
            return premierDTO;
        }).collect(Collectors.toSet());

        PageDTO<PremierDTO> result = new PageDTO<>(collect, premiers.getTotalPages(), premiers.getTotalElements());
        redisUtils.add("cinema:" + cinema + ":premiers:" + page, result);
        return result;
    }

    @Override
    public PageDTO<PremierDTO> getPremiersByFilm(String film, int page) {
        Page<Premier> premierByFilm = premierRepository.getPremierByFilm(film, PageRequest.of(page, 10, Sort.by("price").ascending().and(Sort.by("end").descending())));
        FilmDTO filmById = filmService.getFilmById(film, false, false);
        Set<PremierDTO> collect = premierByFilm.getContent().stream()
                .map(premier -> {
                    PremierDTO premierDTO = new PremierDTO();
                    BeanUtils.copyProperties(premier, premierDTO);
                    premierDTO.setFilm(filmById);
                    premierDTO.setSchedules(scheduleService.getSchedulesByPremier(premier.getId()));
                    User userById = userRepository.findUserById(premier.getCinema());
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(userById, userDTO);
                    premierDTO.setCinema(userDTO);
                    return premierDTO;
                }).collect(Collectors.toSet());
        return new PageDTO<>(collect, premierByFilm.getTotalPages(), premierByFilm.getTotalElements());
    }

    @Override
    public Premier createPremier(Premier premier) {
        Premier save = premierRepository.save(premier);
        premier.getSchedules().forEach(
                schedule -> {
                    schedule.setPremier(save.getId());
                    schedule.setId(UUID.randomUUID().toString());
                    scheduleService.createSchedule(schedule, premier.getPrice());
                }
        );
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

    private PremierDTO getDTO(Premier premier){
        PremierDTO premierDTO = new PremierDTO();
        BeanUtils.copyProperties(premier, premierDTO);
        premierDTO.setFilm(filmService.getFilmById(premier.getFilm(), false, false));
        premierDTO.setSchedules(scheduleService.getSchedulesByPremier(premier.getId()));
        User userById = userRepository.findUserById(premier.getCinema());
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userById, userDTO);
        premierDTO.setCinema(userDTO);
        return premierDTO;
    }
}
