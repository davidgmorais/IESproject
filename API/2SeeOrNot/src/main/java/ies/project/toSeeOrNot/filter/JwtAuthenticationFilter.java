package ies.project.toSeeOrNot.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.dto.UserDTO;
import ies.project.toSeeOrNot.entity.JwtUser;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.exception.AuthenticationFailedException;
import ies.project.toSeeOrNot.utils.JSONUtils;
import ies.project.toSeeOrNot.utils.JWTUtils;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Wei
 * @date 2020/12/2 21:15
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/login");
    }

    /**
     * get userinfo from http request
     * try to do authentication with user's email and password
     * @param httpServletRequest http request
     * @param httpServletResponse http response
     * @return Authentication object
     * @throws AuthenticationException
     * @throws ServletException
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException{
        if (!"POST".equals(httpServletRequest.getMethod())) {
            httpServletRequest.setAttribute("Exception", new AuthenticationServiceException("Only supports POST request method"));
            httpServletRequest.getRequestDispatcher("/error/throw").forward(httpServletRequest, httpServletResponse);
        }
        try {
            User user = new ObjectMapper().readValue(httpServletRequest.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * if successfully authenticated
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        String role = jwtUser.getAuthorities().iterator().next().getAuthority();
        String token = JWTUtils.createToken(jwtUser.getUsername(), jwtUser.getId(), role, false);
        response.setHeader(JWTUtils.getHeader(),  token);
        response.setContentType("application/json;charset=UTF-8");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(jwtUser.getId());
        userDTO.setUserEmail(jwtUser.getUsername());
        userDTO.setUserName(jwtUser.getRealUserName());
        userDTO.setRole(jwtUser.getRole());
        response.getWriter().write(JSONUtils.toJSONString(Result.sucess(HttpStatusCode.OK, userDTO)));
    }

    /**
     * if authentication failed
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        request.setAttribute("Exception", new AuthenticationFailedException());
        request.getRequestDispatcher("/error/throw").forward(request, response);
    }
}
