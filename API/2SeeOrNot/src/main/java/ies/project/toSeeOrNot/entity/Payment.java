package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/4 10:19
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "buyer")
    private int buyer;

    @Column(name = "ticket")
    private int ticket;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "price")
    private Double price;
}
