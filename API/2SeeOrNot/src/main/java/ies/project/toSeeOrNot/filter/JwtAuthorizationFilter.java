package ies.project.toSeeOrNot.filter;
import ies.project.toSeeOrNot.exception.AuthenticationFailedException;
import ies.project.toSeeOrNot.utils.JWTUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author Wei
 * @date 2020/12/3 10:51
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JWTUtils.getHeader());
        //if there is no JwtUtils.getHEADER() in the request header, let it go to the next filter
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        //if there is a token in the request header
        try{
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
        }catch (Exception e){
            request.setAttribute("Exception", e);
            request.getRequestDispatcher("/error/throw").forward(request, response);
        }
        super.doFilterInternal(request, response, chain);
    }

    /**
     * get user identifier(email) from the token
     * @param token jwt token
     * @return Authentication
     */
    private Authentication getAuthentication(String token) {
        String userEmail = JWTUtils.getUserEmail(token);
        String role = JWTUtils.getUserRole(token);
        if (userEmail != null){
            return new UsernamePasswordAuthenticationToken(userEmail,null
                    , Collections.singleton(new SimpleGrantedAuthority(role)));
        }
        return null;
    }
}
