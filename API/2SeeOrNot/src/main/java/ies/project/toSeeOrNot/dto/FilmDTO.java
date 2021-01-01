package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO implements Serializable {
    private String title;

    private String movieId;

    private Date year;

    private Date released;

    private Integer runtime;

    private String director;

    private String plot;

    private Integer like;

    private Double rating;

    private String picture;

    private List<ActorDTO> actors;

    private List<GenreDTO> genres;

    private List<CommentDTO> comments;
}
