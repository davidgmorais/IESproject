package ies.project.toSeeOrNot.utils.consuming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ies.project.toSeeOrNot.entity.Actor;
import ies.project.toSeeOrNot.entity.Genre;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @JsonProperty("Title")
    private String Title;
    @JsonProperty("imdbID")
    private String imdbID;
    @JsonProperty("Year")
    private String Year;
    @JsonProperty("Released")
    private String Released;
    @JsonProperty("Runtime")
    private String Runtime;
    @JsonProperty("Genre")
    private String Genre;
    @JsonProperty("Director")
    private String Director;
    @JsonProperty("Actors")
    private String Actors;
    @JsonProperty("Plot")
    private String Plot;
    @JsonProperty("imdbVotes")
    private String imdbVotes;
    @JsonProperty("imdbRating")
    private String imdbRating;
    @JsonProperty("Poster")
    private String Poster;

    public Movie() {
    }

    @Override
    public String toString() {
        return "Movie{" +
                "Title='" + Title + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", Year='" + Year + '\'' +
                ", Released='" + Released + '\'' +
                ", Runtime='" + Runtime + '\'' +
                ", Genre='" + Genre + '\'' +
                ", Director='" + Director + '\'' +
                ", Actors='" + Actors + '\'' +
                ", Plot='" + Plot + '\'' +
                ", imbdVotes='" + imdbVotes + '\'' +
                ", imbdRating='" + imdbRating + '\'' +
                ", Poster='" + Poster + '\'' +
                '}';
    }
}
