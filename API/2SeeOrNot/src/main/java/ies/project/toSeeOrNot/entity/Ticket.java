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
    private int ticketId;

    @Column(name = "room_id")
    private int roomId;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "seat_id")
    private int seatId;

    @Column(name = "buyer")
    private int buyer;

    @Column(name = "sold")
    private boolean sold;   //is a flag that indicate if this ticket was sold

    @Column(name = "start_time")
    private double price;
}
