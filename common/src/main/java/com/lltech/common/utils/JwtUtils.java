package com.lltech.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * @author Created by L.C.Y on 2018-11-26
 */
public class JwtUtils {
    private static final String SIGN_KEY = "com.lltech.htsdk.@author.L.C.Y";
    private static final byte[] SIGNING_KEY_BYTES = DatatypeConverter.parseBase64Binary(SIGN_KEY);

    private JwtUtils(){}

    /**
     * 生成token.限定有效期
     * @param payload 载荷， map形式装载填充信息
     * @param exp 有效时长，毫秒millis
     * @return 限定有效期token
     */
    public static String generateTokenOnExp(Map<String, Object> payload, long exp) {
        return generateToken(payload, true, exp);
    }

    /**
     * 生成token.永久有效
     * @param payload 载荷
     * @return 永久有效期token
     */
    public static String generateTokenOffExp(Map<String, Object> payload) {
        return generateToken(payload, false, 0);
    }

    /**
     * 生成token
     * @param payload 载荷， map形式装载填充信息
     * @param onExp 是否开启过期时间， 是- true， 否- false
     * @param exp 有效期ms
     * @return token
     */
    public static String generateToken(Map<String, Object> payload , boolean onExp, long exp) {
        // 加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //  管理密钥
        Key signKey = new SecretKeySpec(SIGNING_KEY_BYTES, signatureAlgorithm.getJcaName());

        // JWT实现
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims(payload)
                .signWith(signatureAlgorithm, signKey);
        // 开启过期时间
        if (onExp) {
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + exp));
        }
        return jwtBuilder.compact();
    }

    /**
     * 解析token信息
     * @param token token信息
     * @return Claims, 有效且未过期- claims，
     *                 无效或已过期- null
     */
    public static Claims parse(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(SIGNING_KEY_BYTES)
                    .parseClaimsJws(token).getBody();
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否过期
     * @param token token
     * @return 已过期 true， 未过期 false
     */
    public static boolean isTokenExpired(String token) {
        Claims claims = parse(token);
        return claims == null || (claims.getExpiration() != null && claims.getExpiration().before(DefaultClock.INSTANCE.now()));
    }

    /**
     * 鉴定token信息
     * @param token token信息
     * @return boolean 验证通过 true， 失败（过期或无效） false
     */
    public static boolean validateToken(String token){
        return parse(token) != null;
    }

}
