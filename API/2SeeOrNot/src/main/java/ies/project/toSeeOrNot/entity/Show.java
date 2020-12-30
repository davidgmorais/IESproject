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
 * @date 2020/12/26 16:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "show")
public class Show {
    @Id
    private Integer id;

    @Column(name = "film")
    private Integer film;

    @Column(name = "cinema")
    private Integer cinema;

    @Column(name = "start")
    private Date start;

    @Column(name = "end")
    private Date end;

}
