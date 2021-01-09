package ies.project.toSeeOrNot.dto;

import ies.project.toSeeOrNot.entity.Cinema;
import ies.project.toSeeOrNot.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    private PageDTO<CommentDTO> commentDTOS;

    @Transient
    private int pages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PremierDTO)) return false;
        PremierDTO that = (PremierDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
