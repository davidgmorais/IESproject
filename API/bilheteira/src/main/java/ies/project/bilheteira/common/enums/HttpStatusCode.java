package ies.project.bilheteira.common.enums;

import lombok.Data;

/**
 * @author Wei
 * @date 2020/11/29 10:35
 */
public enum  HttpStatusCode {
    OK(200, "Resquest has been succesfully processed."),
    //fila de mensagens? talvez?
    ACCEPTED(202, "Accepted, the request has been accepted, waiting to be executed"),
    RESOURCE_NOT_FOUND(404, "Resource couldn't be found."),
    METHOD_NOT_ALLOWED(405, "Method not allowed."),
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
