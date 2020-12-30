package ies.project.toSeeOrNot.dto;
import lombok.Data;
import java.io.Serializable;

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
}
