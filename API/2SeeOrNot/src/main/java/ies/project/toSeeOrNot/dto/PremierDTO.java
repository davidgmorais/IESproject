package ies.project.toSeeOrNot.dto;

import ies.project.toSeeOrNot.entity.Cinema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    private int cinema;

    private Date start;

    private Date end;

    private double price;

    private Set<ScheduleDTO> schedules;
}
