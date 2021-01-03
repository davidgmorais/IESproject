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
 * @date 2020/12/26 16:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    private int id;

    @Column(name = "roomId")
    private int roomId;

    @Column(name = "row")
    private int row;

    @Column(name = "column")
    private int column;
}
