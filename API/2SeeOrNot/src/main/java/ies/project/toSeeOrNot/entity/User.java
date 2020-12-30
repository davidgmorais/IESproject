package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Wei
 * @date 2020/11/29 12:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Email
    @NotBlank
    @Column(name = "userEmail", nullable = false)
    private String userEmail;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private Integer role = 0;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "flag", nullable = false)
    private Integer flag; //if 1, user is logically removed

}
