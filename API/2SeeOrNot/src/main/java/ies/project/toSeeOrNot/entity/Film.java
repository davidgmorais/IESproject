package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

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
    private LocalDate year;

    @Column(name = "released", nullable = false)
    private LocalDate released;

    @Column(name = "runtime", nullable = false)
    private int runtime;

    @Column(name = "director", nullable = false)
    private String director;

    @Column(name = "plot", nullable = false)
    private String plot;

    @Column(name = "like", nullable = false)
    private int like;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "picture", nullable = false)
    private String picture;

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }
}
