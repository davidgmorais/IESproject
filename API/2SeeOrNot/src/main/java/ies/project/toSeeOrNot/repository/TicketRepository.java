package ies.project.toSeeOrNot.repository;

import ies.project.toSeeOrNot.entity.Ticket;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/3 19:34
 */
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Integer> {
    Set<Ticket> getTicketsBySchedule(String schedule);

    Ticket getTicketBySeatIdAndSchedule(int seat, String schedule);

    Ticket getTicketByTicketId(int id);
}
