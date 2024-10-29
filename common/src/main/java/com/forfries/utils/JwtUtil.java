package com.forfries.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定秘钥
     *
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    设置的信息
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定签名的时候使用的签名算法，也就是header那部分
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        // 生成JWT的时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        String token = JWT.create()
                .withPayload(claims)
                .withExpiresAt(exp)
                .sign(algorithm);
        return token;
    }

    /**
     * 验证和解析JWT令牌
     *
     * @param token JWT令牌
     * @return DecodedJWT对象，如果验证失败则抛出异常
     */
    public static Map<String, Claim> decodeJWT(String secretKey, String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build(); // 验证签名
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getClaims();
    }
}
