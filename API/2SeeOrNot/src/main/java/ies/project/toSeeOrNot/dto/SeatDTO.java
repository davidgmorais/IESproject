package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/30 16:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO implements Serializable {
    private int id;

    private String x;

    private String y;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatDTO)) return false;
        SeatDTO seatDTO = (SeatDTO) o;
        return id == seatDTO.id;
    }
}