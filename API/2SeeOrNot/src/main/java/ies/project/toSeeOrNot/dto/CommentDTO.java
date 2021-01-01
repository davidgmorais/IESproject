package ies.project.toSeeOrNot.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei
 * @date 2020/12/26 20:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO implements Serializable {
    private int id;

    /**
     * if it is a comment of comment, it will have a parent comment
     */
    private int parentId;

    /**
     * author of the current comment
     */
    private UserDTO author;

    private CinemaDTO cinema;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    private int likes;

    private String film;

    private PremierDTO premier;

    private UserDTO replyto;
}
