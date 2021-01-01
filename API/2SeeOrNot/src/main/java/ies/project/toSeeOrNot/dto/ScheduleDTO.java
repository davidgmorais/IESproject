package ies.project.toSeeOrNot.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/30 16:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO implements Serializable {
    private int id;

    private FilmDTO film;

    private Date start;

    private Date end;

    private RoomDTO room;
}
