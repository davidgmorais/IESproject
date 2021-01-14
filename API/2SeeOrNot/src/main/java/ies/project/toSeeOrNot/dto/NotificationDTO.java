package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Wei
 * @date 2020/12/3 21:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO implements Serializable {
    private int id;

    private UserDTO sender;

    private UserDTO receiver;

    private Date created;

    private String title;

    private String message;

    private Object data;

    private boolean read;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationDTO)) return false;
        NotificationDTO that = (NotificationDTO) o;
        return id == that.id;
    }
}
