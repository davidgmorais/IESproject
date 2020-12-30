package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/11/29 12:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "filmbycountry")
public class FilmByCountry implements Serializable {
    @Id
    private String countryName;

    @Column(name = "film")
    private String film;

    @Column(name = "film_title")
    private String filmTitle;

    @Column(name = "film_released")
    private Date film_released;

    @Column(name = "film_year")
    private Date filmYear;

    @Column(name = "film_rating")
    private Date filmRating;

    @Column(name = "film_likes")
    private Date filmLikes;
}
