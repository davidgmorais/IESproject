package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Wei
 * @date 2021/1/2 18:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaUser implements Serializable {
    private String userName;

    @Email
    @NotBlank
    private String userEmail;

    @NotBlank
    private String password;

    private int role;

    private String avatar;

    private String location;

    private String description;

}
