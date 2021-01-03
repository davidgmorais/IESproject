package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author Wei
 * @date 2020/12/30 16:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO implements Serializable {
    private int id;

    /*
     * number of seats in the room
     */
    private int seat;

    private CinemaDTO cinema;
}
