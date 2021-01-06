package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

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

}
