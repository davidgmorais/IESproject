package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author Wei
 * @date 2020/12/10 17:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO implements Serializable {
    private String genreName;
}
