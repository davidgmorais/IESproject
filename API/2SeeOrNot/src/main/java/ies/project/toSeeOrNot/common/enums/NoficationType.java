package ies.project.toSeeOrNot.common.enums;

import lombok.Data;

/**
 * @author Wei
 * @date 2021/1/1 13:54
 */
public enum NoficationType {
    PREMIER("premier"),
    CINEMA("cinema"),
    COMMENT("comment");

    private final String type;

    NoficationType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
