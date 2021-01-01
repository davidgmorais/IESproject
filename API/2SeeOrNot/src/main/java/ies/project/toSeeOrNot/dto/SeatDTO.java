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
    private Integer id;

    private RoomDTO roomId;

    private Integer row;

    private Integer column;

}
