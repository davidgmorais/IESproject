package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.*;
import ies.project.toSeeOrNot.entity.Payment;
import ies.project.toSeeOrNot.entity.Ticket;
import ies.project.toSeeOrNot.repository.PaymentRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Wei
 * @date 2021/1/5 18:34
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    RoomService roomService;

    @Autowired
    FilmService filmService;

    @Autowired
    UserService userService;

    @Autowired
    SeatService seatService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    CinemaService cinemaService;

    @Autowired
    TicketService ticketService;

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public PaymentDTO buyTicket(Ticket ticket) {
        Ticket sold = ticketService.buyTicket(ticket);

        if (sold == null)
            return null;

        TicketDTO ticketDTO = new TicketDTO();
        UserDTO buyer = userService.getUserById(sold.getBuyer());

        ticketDTO.setPrice(sold.getPrice());

        ticketDTO.setSeat(seatService.getSeatById(sold.getSeatId()));

        ScheduleDTO scheduleDTO = scheduleService.getScheduleById(sold.getSchedule());
        ticketDTO.setStartTime(scheduleDTO.getStart());
        ticketDTO.setEndTime(scheduleDTO.getEnd());

        ticketDTO.setRoomName(scheduleDTO.getRoom().getName());

        CinemaDTO cinemaDTO = cinemaService.getCinemaById(scheduleDTO.getRoom().getCinema());
        ticketDTO.setCinemaEmail(cinemaDTO.getUser().getUserEmail());
        ticketDTO.setCinemaName(cinemaDTO.getUser().getUserName());

        ticketDTO.setFilmTitle(scheduleDTO.getFilm().getTitle());
        ticketDTO.setId(ticket.getTicketId());

        Payment payment = new Payment();
        payment.setDate(LocalDateTime.now());
        payment.setPrice(ticket.getPrice());
        payment.setTicket(ticket.getTicketId());
        payment.setUser(buyer.getId());
        Payment savedPayment = paymentRepository.save(payment);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setDate(payment.getDate());
        paymentDTO.setPrice(ticket.getPrice());
        paymentDTO.setId(savedPayment.getId());
        paymentDTO.setTicket(ticketDTO);
        paymentDTO.setUser(buyer);

        return paymentDTO;
    }
}
