package ies.project.toSeeOrNot.dto;

import ies.project.toSeeOrNot.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/4 10:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO implements Serializable {
    private Integer id;

    private UserDTO user;

    private Ticket ticket;

    private Date date;

    @Column(name = "price")
    private Double price;
}
