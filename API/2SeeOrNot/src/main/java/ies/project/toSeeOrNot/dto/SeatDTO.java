package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author Wei
 * @date 2020/12/30 16:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO implements Serializable {
    private int id;

    private RoomDTO roomId;

    private int row;

    private int column;

}
