package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

/**
 * @author Wei
 * @date 2020/12/26 21:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendDTO implements Serializable {
    private List<UserDTO> followers;

    private List<UserDTO> influencers;
}
