package ies.project.toSeeOrNot.common;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import lombok.Getter;

import java.io.Serializable;

/**
 *
 * @author Wei
 * @date 2020/11/29 10:33
 */

/**
 * Json result, used to communicate with Front-end
 */
@Getter
public class Result implements Serializable {
    private Integer status;
    private String message;
    private Object data;

    private Result(){}

    private Result(Integer status, String message, Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static Result sucess(Object data){
        return sucess(HttpStatusCode.OK, data);
    }

    public static Result sucess(HttpStatusCode code){
        return new Result(code.code(), code.msg(),"");
    }

    public static Result sucess(HttpStatusCode code, Object data){
        return new Result(code.code(), code.msg(),data == null ? "" : data);
    }

    public static Result failure(HttpStatusCode code){
        return new Result(code.code(), code.msg(),"");
    }

    public static Result failure(HttpStatusCode code, Object data){
        return new Result(code.code(), code.msg(), data == null ? "" : data);
    }

    public static Result failure(HttpStatusCode code, String message, Object data){
        return new Result(code.code(), message, data == null ? "" : data);
    }
}
