package ies.project.toSeeOrNot.dto;
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
    private Integer id;

    /**
     * if it is a comment of comment, it will have a parent comment
     */
    private Integer parentId;

    /**
     * author of the current comment
     */
    private UserDTO author;

    private String content;

    private Date created;

    private Integer likes;

    private FilmDTO film;

    private ShowDTO show;

    private UserDTO replyto;
}
