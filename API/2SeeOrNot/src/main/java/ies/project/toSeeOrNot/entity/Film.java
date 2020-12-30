package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @Id
    private String movieId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "year", nullable = false)
    private Date year;

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
