package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/26 16:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room {
    public Room(int seats, int cinema, String name, boolean flag) {
        this.seats = seats;
        this.cinema = cinema;
        this.name = name;
        this.flag = flag;
    }

    public Room(int id, int seats, int cinema, String name, boolean flag) {
        this.id = id;
        this.seats = seats;
        this.cinema = cinema;
        this.name = name;
        this.flag = flag;

    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    /*
     * number of seats in the room
    */
    @Column(name = "seats")
    private int seats;

    @Column(name = "cinema")
    private int cinema;

    @Column(name = "name")
    private String name;

    @Column(name = "flag")
    private boolean flag;

    @Transient
    private Set<String> positions;

}
