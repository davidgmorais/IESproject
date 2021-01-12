package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wei
 * @date 2021/1/7 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favouritefilm")
public class FavouriteFilm {
    @Id
    private int user;

    @Column(name = "film")
    private String film;
}
