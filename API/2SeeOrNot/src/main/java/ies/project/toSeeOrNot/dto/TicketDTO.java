package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/30 18:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TicketDTO implements Serializable {
    private int id;

    private String roomName;

    private String cinemaName;

    private String cinemaEmail;

    private String filmTitle;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private SeatDTO seat;

    private double price;

}
