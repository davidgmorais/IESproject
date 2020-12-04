package ies.project.toSeeOrNot.dto;

import ies.project.toSeeOrNot.entity.Actor;
import ies.project.toSeeOrNot.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO implements Serializable {
    private String title;

    private String movieId;

    private Integer year;

    private Date released;

    private Integer runtime;

    private String director;

    private String plot;

    private Integer like;

    private Double rating;

    private String pictrue;

    private List<Actor> actors;

    private List<Genre> genres;
}
