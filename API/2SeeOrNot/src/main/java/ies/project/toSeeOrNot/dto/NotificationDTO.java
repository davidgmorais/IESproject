package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/3 21:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO implements Serializable {
    private Integer id;

    private UserDTO sender;

    private UserDTO receiver;

    private Date date;

    private String message;

    private Boolean read;
}
