package com.bca.adam.util;

import java.util.HashMap;
import java.util.List;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTTokenizer {
    static String keyStr = "b286bf66c888ea8518e2fc537531a78cd244d9a2a1dd6f7ef8d4012e7d3c3211";
    static byte[] bytes = keyStr.getBytes();
    static SecretKey key = Keys.hmacShaKeyFor(bytes);

    public static void main(String[] args) {
        System.out.println(validateJWT(
                "eyJhbGciOiJIUzUxMiJ9.eyJjcmVhdGVkZGF0ZSI6IlN1biBGZWIgMjggMDA6NTQ6NTIgV0lCIDIwMjEiLCJpc3ZhbGlkIjoiMTYxNDQ0dHJ1ZTg0OTI0MzciLCJzZXNzaW9uaWQiOiI3NjkwYzQ5OTE3NTA0ZDdiOTA0ZGNiMThkM2UzZGYxOSJ9.yetAi4Et2h9tIl0-ee-dAYeNNTSlxjqIc57OY026cG5oBRST9nUhm_ThMu03NBZsmKppOAkpAiIcMyyvkdAtdA"));
    }

    public static String generateJWT(HashMap<String, String> claims) {
        JwtBuilder jwt = Jwts.builder();
        for (HashMap.Entry<String, String> claim : claims.entrySet()) {
            jwt.claim(claim.getKey(), claim.getValue());
        }
        return jwt.signWith(key).compact();
    }

    public static String generateObjectJWT(HashMap<String, Object> claims) {
        JwtBuilder jwt = Jwts.builder();
        for (HashMap.Entry<String, Object> claim : claims.entrySet()) {
            jwt.claim(claim.getKey(), claim.getValue());
        }
        return jwt.signWith(key).compact();
    }

    public static String generateListJWT(List<HashMap<String, String>> claims) {
        JwtBuilder jwt = Jwts.builder();
        int i = 1;
        for (HashMap<String, String> claim : claims) {
            jwt.claim(i + "", claim);
            i++;
        }
        return jwt.signWith(key).compact();
    }

    public static Claims validateJWT(String jwtString) {
        try {
            // JWT OK!
            Jws<Claims> claims = Jwts.parser().setSigningKey(bytes).parseClaimsJws(jwtString);
            return claims.getBody();
        } catch (JwtException e) {
            // don't trust the JWT!
            e.printStackTrace();
            throw e;
        }
    }

}
