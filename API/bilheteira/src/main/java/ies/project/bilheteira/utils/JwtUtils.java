package ies.project.bilheteira.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author Wei
 * @date 2020/11/29 11:28
 */
public class JwtUtils {

    private static final String KEY = "!@#%^&"; //devia ser uma coisa maior e random

    /**
     * criar token,  header.payload, singnature
     */
    public static String getToken(Map<String,String>map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7); //por defeito, expira depois de 7 dias

        JWTCreator.Builder builder = JWT.create();

        map.forEach(builder::withClaim);

        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(KEY));
    }

    //verificacao
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
    }

}
