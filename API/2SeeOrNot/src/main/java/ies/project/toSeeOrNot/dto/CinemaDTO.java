package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

    private int notifications;

    private PageDTO<CommentDTO> comments;

    private PageDTO<PremierDTO> premiers;

    private Set<RoomDTO> rooms;
}
