package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.TicketDTO;
import ies.project.toSeeOrNot.entity.Premier;
import ies.project.toSeeOrNot.entity.Schedule;
import ies.project.toSeeOrNot.entity.Ticket;

/**
 * @author Wei
 * @date 2021/1/4 16:36
 */
public interface TicketService {
    Ticket buyTicket(Ticket ticket);

    void createTickets(Schedule schedule, double price);

    boolean deleteTickets(Schedule schedule);

    TicketDTO getTicketById(int id);

}
