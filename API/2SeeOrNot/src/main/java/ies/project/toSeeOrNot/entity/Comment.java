package ies.project.toSeeOrNot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/26 20:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    private Integer id;

    /**
     * if it is a comment of comment, it will have a parent comment
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * author of the current comment
     */
    @Column(name = "author")
    private Integer author;

    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private Date created;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "film")
    private String film;

    @Column(name = "show")
    private Integer show;

    @Column(name = "replyto")
    private Integer replyto;
}
