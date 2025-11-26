package com.LokeshReddy.SpringSecuityAsymmetricEncryption.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private static final String TOKEN_TYPE="token_type";
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    @Value("${app.security.jwt.accessTokenExpiration}")
    private long accessTokenExpiration;

    @Value("${app.security.jwt.refreshTokenExpiration}")
    private long refreshTokenExpiration;

    public JwtService() throws Exception {
        this.privateKey = KeyUtils.loadPrivateKey("key/local-only/private_key.pem");
        this.publicKey = KeyUtils.loadPublicKey("key/local-only/public_key.pem");
    }
    public String generateAccessToken(final String username){
        Map<String,Object> claims=Map.of(TOKEN_TYPE,"ACCESS_TOKEN");
        return buildToken(username,claims,accessTokenExpiration);//they are just data that your jwt include
    }
    public String generateRefreshToken(final String username){
        Map<String,Object> claims=Map.of(TOKEN_TYPE,"REFRESH_TOKEN");
        return buildToken(username,claims,accessTokenExpiration);//they are just data that your jwt include
    }
    public String buildToken(final String username,final Map<String,Object> claims,final long expiration){
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(this.privateKey)
                .compact();

    }
    public boolean isTokenValid(final String token,final String expectedUsername){
        final String username=extractUsername(token);
        return username.equals(expectedUsername)&&!isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }
    public Claims extractClaims(String token){
        try{
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (JwtException e){
            throw new RuntimeException("Invalid JWT TOken "+e);
        }
    }
    public String refreshAccessToken(final String refreshToken){
        Claims claims=extractClaims(refreshToken);
        if(!"REFRESH_TOKEN".equals(claims.get("TOKEN_TYPE"))){
            throw new RuntimeException("Invalid Refresh Token");
        }
        if(isTokenExpired(refreshToken)){
            throw new RuntimeException("Refresh Token Expired");
        }
        return generateAccessToken(claims.getSubject());
    }

}
