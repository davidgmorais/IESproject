package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.component.RedisUtils;
import ies.project.toSeeOrNot.dto.SeatDTO;
import ies.project.toSeeOrNot.entity.Schedule;
import ies.project.toSeeOrNot.entity.Seat;
import ies.project.toSeeOrNot.entity.Ticket;
import ies.project.toSeeOrNot.repository.SeatRepository;
import ies.project.toSeeOrNot.repository.TicketRepository;
import ies.project.toSeeOrNot.service.ScheduleService;
import ies.project.toSeeOrNot.service.SeatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wei
 * @date 2021/1/3 16:45
 */
@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public Set<SeatDTO> getSeatsByRoom(int room) {
        Set<SeatDTO> cache = (Set<SeatDTO>) redisUtils.getSet("seats:room:" + room);
        if (cache != null)
            return cache;

        Set<Seat> seats = seatRepository.getSeatsByRoomId(room);

        Set<SeatDTO> collect = seats.stream().map(
                seat -> {
                    SeatDTO seatDTO = new SeatDTO();
                    BeanUtils.copyProperties(seat, seatDTO);
                    return seatDTO;
                }
        ).collect(Collectors.toSet());

        redisUtils.storeSet("seats:room:" + room, collect);
        return collect;
    }

    @Override
    public SeatDTO getSeatById(int id) {
        SeatDTO cache = (SeatDTO) redisUtils.get("seat:" + id);
        if (cache != null){
            return cache;
        }
        Seat seat = seatRepository.getSeatById(id);
        SeatDTO seatDTO = new SeatDTO();
        BeanUtils.copyProperties(seat, seatDTO);

        redisUtils.add("seat:" + id, cache);
        return seatDTO;
    }

    @Override
    public void save(Seat seat) {
        seatRepository.save(seat);
    }

    @Override
    public void getSoldSeatsAndFreeSeats(Set<SeatDTO> sold, Set<SeatDTO> free, String scheduleId) {
        Set<Ticket> tickets = ticketRepository.getTicketsBySchedule(scheduleId);

        tickets.forEach(
                ticket -> {
                    Seat seat = seatRepository.getSeatById(ticket.getSeatId());
                    SeatDTO seatDTO = new SeatDTO();
                    BeanUtils.copyProperties(seat, seatDTO);
                    if (ticket.isSold()){
                        sold.add(seatDTO);
                    }else{
                        free.add(seatDTO);
                    }
                }
        );
    }
}
