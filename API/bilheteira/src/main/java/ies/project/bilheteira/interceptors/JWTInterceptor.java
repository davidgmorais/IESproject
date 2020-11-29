package ies.project.bilheteira.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ies.project.bilheteira.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wei
 * @date 2020/9/2 12:30
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandler------------------");
        //obter o token que vem no header
        String token = request.getHeader("token");

        DecodedJWT verify = JwtUtils.verify(token);//verificacao. isto pode gerar um erro, depois ainda tenho de fazer um exceptionHandler

        return true;
    }
}
