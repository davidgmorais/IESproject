package ies.project.toSeeOrNot.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/30 16:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO implements Serializable {
    private String id;

    private FilmDTO film;

    private LocalDateTime start;

    private LocalDateTime end;

    private RoomDTO room;

    private Set<SeatDTO> sold;

    private Set<SeatDTO> free;

    @Transient
    private int solds;
}
