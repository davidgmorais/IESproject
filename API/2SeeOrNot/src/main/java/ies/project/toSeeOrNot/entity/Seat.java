package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Wei
 * @date 2020/12/26 16:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private int id;

    @Column(name = "roomId")
    private int roomId;

    @Column(name = "y")
    private String y;

    @Column(name = "x")
    private String x;

    @Column(name = "flag")
    private boolean flag;
}
