package ies.project.toSeeOrNot.controller;
import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.exception.AuthenticationFailedException;
import ies.project.toSeeOrNot.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Wei
 * @date 2020/12/2 21:22
 */

/**
 * Exception hanlder
 * returns a Json data instead of an exception
 */
@RestControllerAdvice
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

    @ExceptionHandler(AuthenticationFailedException.class)
    public Result AuthenticationFailedExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.AUTHENTICATION_FAILD);
    }

    @ResponseBody
    @ExceptionHandler(ExpiredJwtException.class)
    public Result ExpiredJwtExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.AUTHENTICATION_FAILD, e.getMessage(), null);
    }

    @ResponseBody
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Result InternalAuthenticationServiceExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.AUTHENTICATION_FAILD, e.getMessage(), null);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public Result AccessDeniedExceptionnHandler(Exception e){
        return Result.failure(HttpStatusCode.ACCESS_DENIED, e.getMessage(), null);
    }
}
