package ies.project.bilheteira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Wei
 * @date 2020/11/29 12:45
 */
@Data
@AllArgsConstructor
public class User implements Serializable {
    private final Integer id;
    private  String userName;
    private final String userEmail;
    private transient final String password;
    private Integer role;
    private transient final Integer flag; //se for 1, o user esta removido logicamente
}
