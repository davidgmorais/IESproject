package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
}
