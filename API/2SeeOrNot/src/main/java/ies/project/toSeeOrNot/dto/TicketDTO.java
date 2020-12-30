package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/30 18:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO implements Serializable {
    private Integer id;

    private RoomDTO room;

    private CinemaDTO cinemaDTO;

    private FilmDTO film;

    private Date startTime;

    private Date endTime;

    private SeatDTO seat;

    private UserDTO buyer;

    private Boolean sold;

    private Double price;
}
