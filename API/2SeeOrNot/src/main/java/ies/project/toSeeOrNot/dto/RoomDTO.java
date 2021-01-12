package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

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
    private int seats;

    private int cinema;

    private String name;

    private Set<SeatDTO> positions;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomDTO)) return false;
        RoomDTO that = (RoomDTO) o;
        return id == that.id;
    }
}
