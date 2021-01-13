package ies.project.toSeeOrNot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Wei
 * @date 2020/12/26 16:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "uuid")
    private String id;

    @Column(name = "premier")
    private int premier;

    @Column(name = "start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;

    @Column(name = "end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;

    @Column(name = "room")
    private int room;

    @Column(name = "solds")
    private int solds;

}
