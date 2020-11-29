package ies.project.bilheteira.common;

import ies.project.bilheteira.common.enums.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author Wei
 * @date 2020/11/29 10:33
 */
@Data
@AllArgsConstructor
public class Result implements Serializable {
    private final Integer statusCode;
    private final String message;
    private final Object data;

    public static Result sucess(Object data){
        return builResult(HttpStatusCode.OK, data);
    }

    public static Result builResult(HttpStatusCode code, Object data){
        return new Result(code.code(), code.msg(), data);
    }
}
