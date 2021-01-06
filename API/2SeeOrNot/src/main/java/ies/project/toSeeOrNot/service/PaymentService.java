package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.dto.PaymentDTO;
import ies.project.toSeeOrNot.entity.Payment;
import ies.project.toSeeOrNot.entity.Ticket;

/**
 * @author Wei
 * @date 2021/1/5 18:32
 */
public interface PaymentService {
    PaymentDTO buyTicket(Ticket ticket);
}
