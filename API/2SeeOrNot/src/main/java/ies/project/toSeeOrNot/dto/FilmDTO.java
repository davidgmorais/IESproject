package ies.project.toSeeOrNot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO implements Serializable {
    private String title;

    private String movieId;

    private int year;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate released;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private int runtime;

    private String director;

    private String plot;

    private int like;

    private double rating;

    private String picture;

    private Set<ActorDTO> actors;

    private Set<GenreDTO> genres;

    private Set<CommentDTO> comments;

    private int pages; // number of pages of comments
}
