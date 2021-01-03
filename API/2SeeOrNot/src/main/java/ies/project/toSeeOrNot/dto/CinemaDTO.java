package ies.project.toSeeOrNot.dto;

import ies.project.toSeeOrNot.entity.Film;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

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
}
