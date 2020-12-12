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
 * @date 2020/11/29 12:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "film")
public class Film{
    @Column(name = "title", nullable = false)
    private String title;

    @Id
    private String movieId;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "released", nullable = false)
    private Date released;

    @Column(name = "runtime", nullable = false)
    private Integer runtime;

    @Column(name = "director", nullable = false)
    private String director;

    @Column(name = "plot", nullable = false)
    private String plot;

    @Column(name = "like", nullable = false)
    private Integer like;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "picture", nullable = false)
    private String picture;
}
