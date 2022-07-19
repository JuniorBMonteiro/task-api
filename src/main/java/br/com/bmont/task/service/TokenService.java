package br.com.bmont.task.service;

import br.com.bmont.task.model.User;
import br.com.bmont.task.response.TokenResponse;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${task.jwt.expiration}")
    private String expiration;
    @Value("${task.jwt.secret}")
    private String secret;

    public TokenResponse createToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Long.parseLong(expiration));
        String token = Jwts.builder()
                .setIssuer("task API")
                .setIssuedAt(now)
                .setSubject(user.getUsername())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        String type = "Bearer";
        return new TokenResponse(token, type);
    }

    public boolean isValidToken(String token){
       try {
           Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
           return true;
       }catch (Exception e){
           return false;
       }
    }

    public String getUsernameByToken(String token) {
       return Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
