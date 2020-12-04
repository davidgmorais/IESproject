package ies.project.toSeeOrNot.dto;

import ies.project.toSeeOrNot.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/4 10:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String id;

    private UserDTO user;

    private Ticket ticket;

    private Date date;
}
