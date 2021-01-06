package ies.project.toSeeOrNot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Wei
 * @date 2021/1/5 17:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO implements Serializable {
    private int id;

    private String userName;

    private String userEmail;

    private int role;

    private int requests;
}
