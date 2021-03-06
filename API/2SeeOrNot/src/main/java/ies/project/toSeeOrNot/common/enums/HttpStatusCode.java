package ies.project.toSeeOrNot.common.enums;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Wei
 * @date 2020/11/29 10:35
 */
public enum  HttpStatusCode{
    OK(200, "Resquest has been succesfully processed."),
    ACCESS_DENIED(401, "ACCESS_DENIED"),
    AUTHENTICATION_FAILD(403, "Authentication faild."),
    RESOURCE_NOT_FOUND(404, "Resource couldn't be found."),
    METHOD_NOT_ALLOWED(405, "Method not allowed."),
    USER_ALREADY_EXISTS(407, "User already exists."),
    BAD_REQUEST(410, "Bad request."),
    REQUEST_BODY_TOO_LARGE(413, "Request body too long"),
    REQUEST_URI_TOO_LONG(414, "Request-URI too long");



    private final Integer statusCode;
    private final String msg;

    HttpStatusCode(int statusCode, String msg){
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public Integer code(){
        return this.statusCode;
    }

    public String msg(){
        return this.msg;
    }
}
