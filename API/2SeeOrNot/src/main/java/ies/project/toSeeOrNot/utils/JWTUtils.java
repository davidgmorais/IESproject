package ies.project.toSeeOrNot.utils;
import ies.project.toSeeOrNot.entity.JwtUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Wei
 * @date 2020/11/29 11:28
 */
@Slf4j
@ConfigurationProperties(prefix = "jwt")
public class JWTUtils {
    private static String header;
    public static String getHeader(){ return header; }


    @Value("${jwt.header}")    // read header from configuration file
    public void setHeader(String header){ JWTUtils.header = header; }

    private static String key;
    @Value("${jwt.key}")
    public void setKey(String key){ JWTUtils.key = key; }

    private static String issure;
    @Value("${jwt.issure}")
    public void setIssure(String issure){ JWTUtils.issure = issure; }

    // EXPIRATION * 1000 = 1 hour
    private static long expiration;
    @Value("${jwt.expiration}")
    public void setExpiration(long expiration){ JWTUtils.expiration = expiration; }

    // EXPIRATION_REMEMBER * 1000 = 7 dayn
    private static long expiration_remember;
    @Value("${jwt.expiration_remember}")
    public void setExpiration_remember(long expiration_remember){ JWTUtils.expiration_remember = expiration_remember; }

    /**
     * create a token
     * @param email user's email
     * @param isRememberMe if user login with option "RememberMe"
     * @return
     */
    public static String createToken(String email, Integer id, String role, boolean isRememberMe) {
        long expr = isRememberMe ? expiration_remember : expiration;
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Claims.SUBJECT, email);
        claims.put("role", role);
        claims.put("id", id);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, key)
                .setIssuer(issure)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expr * 1000))
                .compact();
    }

    /**
     * create a token
     * @param claims claims
     * @param isRememberMe if user login with option "RememberMe"
     * @return
     */
    public static String createToken(Map<String, Object> claims, boolean isRememberMe) {
        long expr = isRememberMe ? expiration_remember : expiration;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, key)
                .setIssuer(issure)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expr * 1000))
                .compact();
    }

    /**
     * create a jwt token
     * @param userDetails spring security User
     * @param isRememberMe if user's login with option "RememberMe"
     * @return jwt token
     */
    public static String createToken(UserDetails userDetails, boolean isRememberMe) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Claims.SUBJECT, userDetails.getUsername());
        claims.put(Claims.ISSUED_AT, new Date());
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        claims.put("role", role);
        return createToken(claims, isRememberMe);
    }

    /**
     * create a jwt token
     * @param claims claims
     * @param expireTime expire time (seconds)
     * @return token
     */
    public static String createToken(Map<String, Object> claims, int expireTime) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, key)
                .setIssuer(issure)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime * 1000))
                .compact();
    }

    // retirar o username do token
    public static String getUserEmail(String token){
        return getTokenBody(token).getSubject();
    }


    public static int getUserId(String token){
        return (int)getTokenBody(token).get("id");
    }
    /**
     *
     * @param token jwt token
     * @return if token is expirated
     */
    public static boolean isExpirated(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     *
     * @param token jwt token
     * @return claims
     */
    public static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * refresh the token
     * @param token jwt token
     * @param isRememberMe
     * @return token refreshed
     */
    public static String refreshToken(String token, boolean isRememberMe) {
        Claims claims = getTokenBody(token);
        claims.put(Claims.ISSUED_AT, new Date());
        return createToken(claims, isRememberMe);
    }

    /**
     * validate the jwt token
     * @param token jwt token
     * @param userDetails spring security User
     * @return true if token is valid
     */
    public static Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        //user.getUsername returns user's email
        return (getUserEmail(token).equals(user.getUsername()) && !isExpirated(token));
    }

    public static String getUserRole(String token) {
        return (String) getTokenBody(token).get("role");
    }

}
