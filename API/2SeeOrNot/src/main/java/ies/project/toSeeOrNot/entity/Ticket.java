package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Wei
 * @date 2020/12/3 21:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "show")
    private Integer show;

    @Column(name = "seat_id")
    private Integer seatId;

    @Column(name = "buyer")
    private Integer buyer;

    @Column(name = "sold")
    private Boolean sold;   //is a flag that indicate if this ticket was sold

    @Column(name = "start_time")
    private Double price;
}
