package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Wei
 * @date 2021/1/5 16:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registerrequest")
public class RegisterRequest implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String userEmail;

    @Column(name = "username")
    private String userName;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "password")
    private String password;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "accepted")
    private boolean accepted;

    @Column(name = "processed")
    private boolean processed;
}
