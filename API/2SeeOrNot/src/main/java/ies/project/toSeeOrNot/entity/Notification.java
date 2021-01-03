package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/3 21:34
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class Notification {
    @Id
    private int id;

    @Column(name = "sender")
    private int sender;

    @Column(name = "receiver")
    private int receiver;

    @Column(name = "created")
    private Date created;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "type")
    private String type;        //notification of comment or premier or cinema

    @Column(name = "data")
    private int data;       //id of data (comment, premier, cinema)

    @Column(name = "read")
    private boolean read;
}
