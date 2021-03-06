package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
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
    public Film(String title, String movieId, int year, Date released, int runtime, String director, String plot, int like, double rating, String picture) {
        this.title = title;
        this.movieId = movieId;
        this.year = year;
        this.released = released;
        this.runtime = runtime;
        this.director = director;
        this.plot = plot;
        this.likes = 0;
        this.rating = 0.0;
        this.picture = picture;
    }

    @Id
    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "released", nullable = false)
    private Date released;

    @Column(name = "runtime", nullable = false)
    private int runtime;

    @Column(name = "director", nullable = false)
    private String director;

    @Column(name = "plot", nullable = false)
    private String plot;

    @Column(name = "likes", nullable = false)
    private int likes;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "picture", nullable = false)
    private String picture;

    @Transient
    private Set<String> genre;

    @Transient
    private Set<String> actors;

    @Transient
    private Set<String> country;

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }
}
