package ies.project.toSeeOrNot.controller;
import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.exception.AuthenticationFailedException;
import ies.project.toSeeOrNot.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wei
 * @date 2020/12/2 21:22
 */

/**
 * Exception hanlder
 * returns a Json data instead of an exception
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public Result UserNotFoundExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.USER_NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationServiceException.class)
    public Result AuthenticationServiceExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.BAD_REQUEST, e.getMessage(), null);
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationFailedException.class)
    public Result AuthenticationFailedExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.AUTHENTICATION_FAILD);
    }

    @ResponseBody
    @ExceptionHandler(ExpiredJwtException.class)
    public Result ExpiredJwtExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.AUTHENTICATION_FAILD, e.getMessage(), null);
    }


}
