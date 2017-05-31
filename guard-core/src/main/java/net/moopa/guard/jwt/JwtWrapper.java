package net.moopa.guard.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.moopa.guard.config.Configs;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Moopa on 24/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class JwtWrapper {
    //init
    private static final String secretKey = Configs.get("jwt.secret");
    private static final String issuer = Configs.get("jwt.issuer");
    //HMAC
    private static Algorithm algorithmHS;
    //verify
    private static JWTVerifier jwtVerifier;

    static{

        try {
            algorithmHS = Algorithm.HMAC256(secretKey);
            jwtVerifier = JWT.require(algorithmHS).withIssuer(issuer).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public static String getJwt(String now){
        return JWT.create()
                .withIssuer(issuer)
                .withClaim("created",now)
                .sign(algorithmHS);
    }

    public static String getJwt(JsonObject jsonObject){
        JWTCreator.Builder builder = JWT.create();
        for(Map.Entry<String,JsonElement> entry : jsonObject.entrySet()){
            builder.withClaim(entry.getKey(),entry.getValue().getAsString());
        }
        return builder.sign(algorithmHS);
    }

    public static DecodedJWT verifyAndDecodeJwt(String token){
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = jwtVerifier.verify(token);
        }catch (JWTVerificationException exception){
            return null;
        }

        return decodedJWT;
    }

    public static String getValInPayload(String token,String key){
        return verifyAndDecodeJwt(token).getClaim(key).asString();
    }


}
