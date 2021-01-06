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
public class StarredIn {
    @Id
    private String actor;

    @Column(name = "film")
    private String film;

    @Column(name = "personage")
    private String personage;
}
