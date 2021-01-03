package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/30 22:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PremierDTO implements Serializable {
    private int id;

    private FilmDTO film;

    private CinemaDTO cinema;

    private Date start;

    private Date end;
}
