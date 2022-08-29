package com.auth.jwt.security.jwt;

import com.auth.jwt.user.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("$(app.jwt.secret)")
    private String secret;

    public String generateToken(UserDetailsImpl userDetails){
        return generateTokenFromUserName(userDetails.getUsername());
    }

    public String generateTokenFromUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 86400000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getEmailFromJwtToken(String jwt) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody().getSubject();
    }
}
