package com.example.jwtspringsecurity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String SECRET_KEY;
    @Value("${jwt.refreshSecretKey}")
    private String SECRET_KEY_REFRESH_TOKEN;

    private Key getSigningKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes()); //  đổi về dạng key
    }

    public String extractUsername(String token, boolean isRefreshToken) {
        return extractClaim(token, Claims::getSubject, isRefreshToken);
    }

    public Date extractExpiration(String token, boolean isRefreshToken) {
        return extractClaim(token, Claims::getExpiration, isRefreshToken);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, boolean isRefreshToken) {
        final Claims claims = extractAllClaims(token, isRefreshToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, boolean isRefreshToken) {
        String key = isRefreshToken ? SECRET_KEY_REFRESH_TOKEN : SECRET_KEY; // toán  tử ba ngôi
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token, boolean isRefreshToken) {
        return extractExpiration(token, isRefreshToken).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails, boolean isRefreshToken) {
        final String username = extractUsername(token, isRefreshToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, isRefreshToken));
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60  * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, getSigningKey(SECRET_KEY_REFRESH_TOKEN))
                .compact();
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .signWith(SignatureAlgorithm.HS256, getSigningKey(SECRET_KEY))
                .compact();
    }
//    private List<String> invalidatedTokens = new ArrayList<>();
//    // Thêm phương thức để vô hiệu hóa token
//    public void invalidateToken(String token) {
//        invalidatedTokens.add(token);
//    }
//
//    // Phương thức kiểm tra xem token có bị vô hiệu hóa hay không
//    public boolean isTokenInvalidated(String token) {
//        return invalidatedTokens.contains(token);
//    }

}
