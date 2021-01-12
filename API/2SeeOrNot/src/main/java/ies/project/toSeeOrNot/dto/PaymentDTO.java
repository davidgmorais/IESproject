package ies.project.toSeeOrNot.dto;

import ies.project.toSeeOrNot.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/4 10:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO implements Serializable {
    private int id;

    private UserDTO user;

    private TicketDTO ticket;

    private LocalDateTime date;

    private double price;

}
