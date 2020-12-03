package ies.project.toSeeOrNot.dto;
import ies.project.toSeeOrNot.entity.Film;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author Wei
 * @date 2020/11/29 12:45
 */
@Data
public class UserDTO implements Serializable {
    private Integer id;

    private String userName;

    private String userEmail;

    private Integer role;

    private List<Film> favourites;
}
