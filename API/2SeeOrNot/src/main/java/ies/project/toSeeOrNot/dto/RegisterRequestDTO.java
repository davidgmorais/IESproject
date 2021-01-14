package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Wei
 * @date 2021/1/5 20:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO implements Serializable {
    private int id;

    private String userEmail;

    private String userName;

    private String location;

    private String description;

    private String password;

    private LocalDateTime created;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisterRequestDTO)) return false;
        RegisterRequestDTO that = (RegisterRequestDTO) o;
        return id == that.id;
    }
}
