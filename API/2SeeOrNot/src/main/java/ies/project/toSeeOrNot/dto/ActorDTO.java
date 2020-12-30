package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * @author Wei
 * @date 2020/12/10 17:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorDTO implements Serializable {
    private String actor;
    private String personage;
}
