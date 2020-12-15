package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Wei
 * @date 2020/12/3 20:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "starredin")
public class Actor {
    @Id
    private String actor;

    @Column(name = "personage")
    private String personage;

    @Column(name = "film")
    private String film;

    public Actor(String actor, String personage){
        this.actor = actor;
        this.personage = personage;
    }
}
