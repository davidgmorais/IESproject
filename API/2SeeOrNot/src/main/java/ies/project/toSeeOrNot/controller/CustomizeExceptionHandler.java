package ies.project.toSeeOrNot.controller;
import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

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
    @ExceptionHandler({InternalAuthenticationServiceException.class,
            AuthenticationFailedException.class,
            AuthenticationServiceException.class})
    public Result AuthenticationExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.AUTHENTICATION_FAILD, e.getMessage(), null);
    }


    @ResponseBody
    @ExceptionHandler(ExpiredJwtException.class)
    public Result JwtExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.ACCESS_DENIED, "Token is invalid!", null);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public Result AccessDeniedExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.ACCESS_DENIED, e.getMessage(), null);
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result MethodNotAllowedExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.METHOD_NOT_ALLOWED, e.getMessage(), null);
    }

    @ResponseBody
    @ExceptionHandler({UserNotFoundException.class,
            FilmNotFoundException.class,
            CommentNotFoundException.class,
            PremierNotFoundException.class})
    public Result ResourceNotFoundExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.RESOURCE_NOT_FOUND, e.getMessage(), null);
    }


    @ResponseBody
    @ExceptionHandler({InvalidCommentException.class, UserAlreadyExistsException.class})
    public Result BadRequestExceptionHandler(Exception e){
        return Result.failure(HttpStatusCode.BAD_REQUEST, e.getMessage(), null);
    }
}
