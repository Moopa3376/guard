package net.moopa3376.guard.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.moopa3376.guard.common.ShortUUIDUtil;
import net.moopa3376.guard.config.GuardConfigs;
import net.moopa3376.guard.exception.GuardError;
import net.moopa3376.guard.exception.GuardException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Moopa on 24/05/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public class JwtWrapper {
    //init
    private static final String secretKey = GuardConfigs.get("jwt.secret");
    private static final String issuer = GuardConfigs.get("jwt.issuer");
    //HMAC
    private static Algorithm algorithmHS;
    //verify
    private static JWTVerifier jwtVerifier;

    private static JsonParser jsonParser = new JsonParser();

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
                .withClaim("uuid", ShortUUIDUtil.randomUUID())
                .withExpiresAt(new Date(now+30000))
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
            throw new GuardException(GuardError.JWT_NOTCORRECT,"login to get right token");
        }

        return decodedJWT;
    }

    //该方法用于更新jwt中payload的某个值
    public static String updateJwt(String token,String key,String value){
        //首先划分token,找到payload
        String[] tokens = token.split(".");
        String payload = tokens[1];

        JsonObject jsonObject = (JsonObject) jsonParser.parse(payload);

        jsonObject.addProperty(key,value);

        return getJwt(jsonObject);
    }

    public static String getValInPayload(String token,String key){
        return verifyAndDecodeJwt(token).getClaim(key).asString();
    }


}
