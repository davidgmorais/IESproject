package ies.project.toSeeOrNot.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/30 16:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowDTO implements Serializable {
    private Integer id;

    private FilmDTO film;

    @Column(name = "cinema")
    private CinemaDTO cinema;

    @Column(name = "start")
    private Date start;

    @Column(name = "end")
    private Date end;

    @Column(name = "room")
    private RoomDTO room;
}
