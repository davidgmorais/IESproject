package ies.project.toSeeOrNot.dto;
import lombok.Data;
import java.io.Serializable;

/**
 * @author Wei
 * @date 2020/11/29 12:45
 */
@Data
public class UserDTO implements Serializable {
    private int id;

    private String userName;

    private String userEmail;

    private int notifications;

    private Integer role;
}
