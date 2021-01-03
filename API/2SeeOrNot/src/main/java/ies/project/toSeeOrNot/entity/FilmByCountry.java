package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
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
}
