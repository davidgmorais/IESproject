package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Wei
 * @date 2020/12/3 21:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDTO implements Serializable {
    private UserDTO user;

    private String location;

    private String description;

    private int followers;

    private Set<CommentDTO> comments;

    private Set<PremierDTO> premiers;

    private Set<RoomDTO> rooms;
}
