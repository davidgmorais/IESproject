package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/30 22:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "premier")
public class Premier {
    public Premier(int id, String film, int cinema, Date start, Date end, double price) {
        this.id = id;
        this.film = film;
        this.cinema = cinema;
        this.start = start;
        this.end = end;
        this.price = price;
    }

    public Premier(String film, int cinema, Date start, Date end, double price) {
        this.film = film;
        this.cinema = cinema;
        this.start = start;
        this.end = end;
        this.price = price;
    }

    @Id
    private int id;

    @Column(name = "film")
    private  String film;

    @Column(name = "cinema")
    private  int cinema;

    @Column(name = "start")
    private Date start;

    @Column(name = "end")
    private Date end;

    @Column(name = "price")
    private double price;

    @Transient
    private Set<Schedule> schedules;
}
