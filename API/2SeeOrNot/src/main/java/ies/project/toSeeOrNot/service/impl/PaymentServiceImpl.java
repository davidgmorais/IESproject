package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.*;
import ies.project.toSeeOrNot.entity.Payment;
import ies.project.toSeeOrNot.entity.Ticket;
import ies.project.toSeeOrNot.repository.PaymentRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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
    public PaymentDTO buyTicket(Ticket ticket, int buyerId) {
        Ticket sold = ticketService.buyTicket(ticket);

        if (sold == null)
            return null;

        TicketDTO ticketDTO = new TicketDTO();
        UserDTO buyer = userService.getUserById(buyerId);

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
        payment.setBuyer(buyer.getId());
        Payment savedPayment = paymentRepository.save(payment);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setDate(payment.getDate());
        paymentDTO.setPrice(ticket.getPrice());
        paymentDTO.setId(savedPayment.getId());
        paymentDTO.setTicket(ticketDTO);
        paymentDTO.setUser(buyer);

        return paymentDTO;
    }

    @Override
    public PageDTO<PaymentDTO> getPaymentByUser(int user, int page) {
        Page<Payment> payments = paymentRepository.getPaymentsByBuyer(user, PageRequest.of(page, 10, Sort.by("date").descending()));
        Set<PaymentDTO> collect = payments.getContent().stream()
                .map(payment -> {
                    TicketDTO ticket = ticketService.getTicketById(payment.getTicket());
                    payment.setDate(LocalDateTime.now());
                    payment.setPrice(ticket.getPrice());
                    payment.setTicket(ticket.getId());
                    payment.setBuyer(user);
                    Payment savedPayment = paymentRepository.save(payment);

                    PaymentDTO paymentDTO = new PaymentDTO();
                    paymentDTO.setDate(payment.getDate());
                    paymentDTO.setPrice(ticket.getPrice());
                    paymentDTO.setId(savedPayment.getId());
                    paymentDTO.setTicket(ticket);
                    paymentDTO.setUser(userService.getUserById(user));
                    return paymentDTO;
                }).collect(Collectors.toSet());

        return new PageDTO<>(collect, payments.getTotalPages(), payments.getTotalElements());
    }

}
