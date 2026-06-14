package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.auth.LoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService
{
    @Value("${jwt.secret}")
    private String secretKey;
    public String generateToken(LoginDTO loginDTO)
    {
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(loginDTO.getEmail())
                .issuer("ML")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+(60*10*1000)))
                .and()
                .signWith(generateKey())
                .compact();
    }
    public String extractEmail(String token)
    {
        return extractClaims(token,claims->claims.getSubject());
    }
    public Date extractExpiration(String token)
    {
        return extractClaims(token,claims->claims.getExpiration());
    }
    public <T> T extractClaims(String token, Function<Claims,T> claimResolver)
    {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }
    public Claims extractClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }
    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        return extractEmail(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    public String getSecretKey()
    {
        return secretKey;
    }
    public SecretKey generateKey()
    {
        byte[] decode = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }


}
