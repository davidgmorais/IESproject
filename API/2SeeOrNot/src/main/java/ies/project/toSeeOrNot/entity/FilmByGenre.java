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
 * @date 2020/12/3 21:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "filmbygenre")
public class FilmByGenre implements Serializable{
    @Id
    private String genreName;

    @Column(name = "film")
    private String film;
}
