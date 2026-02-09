package com.aracridav.aracridavback.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServices {

    private static final String JWT_SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
    private static final Long JWT_TIME_VALIDITY = (long) (1000 * 60 * 2);
    private static final Long JWT_TIME_REFRESH_VALIDITY = (long) (1000 * 60 * 60 * 24);

    public String getToken(UserDetails user, String role) {
        return getToken(new HashMap<>(), user, role);
    }
    
    public String getRefreshToken(UserDetails user, String role) {
        return getRefreshToken(new HashMap<>(), user, role);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user, String role) {
        extraClaims.put(role, role);
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TIME_VALIDITY))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    private String getRefreshToken(Map<String, Object> extraClaims, UserDetails user, String role) {
        extraClaims.put(role, role);
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TIME_REFRESH_VALIDITY))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private static Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String getUserNameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private static Claims getAllClaims(String token)    {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public static <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

}