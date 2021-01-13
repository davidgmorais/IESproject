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
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

    /**
     * if it is a comment of comment, it will have a parent comment
     */
        @Column(name = "parent_id")
        private int parentId;

        /**
         * author of the current comment
         */
        @Column(name = "author")
        private int author;

        @Column(name = "cinema")
        private int cinema;

        @Column(name = "content")
        private String content;

        @Column(name = "created")
        private Date created;

        @Column(name = "likes")
        private int likes;

        @Column(name = "film")
        private String film;

        @Column(name = "premier")
        private int premier;

        @Column(name = "replyto")
        private int replyto;

        @Column(name = "flag")
        private boolean flag;

}
