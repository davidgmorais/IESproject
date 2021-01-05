package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.SeatDTO;
import ies.project.toSeeOrNot.dto.TicketDTO;
import ies.project.toSeeOrNot.entity.Premier;
import ies.project.toSeeOrNot.entity.Schedule;
import ies.project.toSeeOrNot.entity.Ticket;
import ies.project.toSeeOrNot.repository.TicketRepository;
import ies.project.toSeeOrNot.service.PaymentService;
import ies.project.toSeeOrNot.service.ScheduleService;
import ies.project.toSeeOrNot.service.SeatService;
import ies.project.toSeeOrNot.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

/**
 * @author Wei
 * @date 2021/1/4 16:36
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    SeatService seatService;

    @Autowired
    PaymentService paymentService;

    private final Object monitor = new Object();

    @Override
    public Ticket buyTicket(Ticket ticket) {
        Ticket result = ticketRepository.getTicketBySeatIdAndSchedule(ticket.getSeatId(), ticket.getSchedule());

        if (result == null || result.isSold()){
            return null;
        }

        synchronized (monitor){
            result = ticketRepository.getTicketBySeatIdAndSchedule(ticket.getSeatId(), ticket.getSchedule());

            if (result == null || result.isSold()){
                return null;
            }

            result.setSold(true);
            ticketRepository.save(result);
            scheduleService.soldsUpdade(ticket.getSchedule(), 1);
            return result;
        }
    }

    @Override
    public void createTickets(Premier premier) {
        premier.getSchedules().forEach(
                schedule -> {
                    schedule.setPremier(premier.getId());
                    schedule.setId(UUID.randomUUID().toString());
                    scheduleService.createSchedule(schedule, premier.getPrice());
                }
        );
    }

    @Override
    public void createTickets(Schedule schedule, double price) {
        Set<SeatDTO> seats = seatService.getSeatsByRoom(schedule.getRoom());

        seats.forEach(seat-> {
            Ticket ticket = new Ticket();
            ticket.setBuyer(-1); // admin's id
            ticket.setPrice(price);
            ticket.setRoomId(schedule.getRoom());
            ticket.setSchedule(schedule.getId());
            ticket.setSeatId(seat.getId());
            ticket.setSold(false);
            ticketRepository.save(ticket);
        });
    }

    @Override
    public synchronized boolean deleteTickets(Schedule schedule) {
        if (schedule.getSolds() != 0)
            return false;

        Set<Ticket> tickets = ticketRepository.getTicketsBySchedule(schedule.getId());

        tickets.forEach(ticket -> {
            ticketRepository.delete(ticket);
        });
        return true;
    }
}
