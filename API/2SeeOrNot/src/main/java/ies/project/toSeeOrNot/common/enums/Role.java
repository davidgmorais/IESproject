package ies.project.toSeeOrNot.common.enums;

import java.util.Map;

/**
 * @author Wei
 * @date 2020/12/3 14:11
 */
public enum Role {
    USER(0, "ROLE_USER"),
    CINEMA(1, "ROLE_CINEMA"),
    ADMIN(2, "ROLE_ADMIN");


    private final Integer roleCode;
    private final String role;
    private static final Map<Integer, Role> map = Map.of(0, USER, 1, CINEMA, 2, ADMIN);

    private Role(Integer roleCode, String role){
        this.roleCode = roleCode;
        this.role = role;
    }

    public String getRole(){
        return role;
    }

    public Integer getRoleCode(){
        return roleCode;
    }

    public static Role getRole(Integer roleCode){
        return map.get(roleCode);
    }

}
