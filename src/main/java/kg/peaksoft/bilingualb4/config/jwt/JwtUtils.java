package kg.peaksoft.bilingualb4.config.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kg.peaksoft.bilingualb4.config.JwtConfig;
import kg.peaksoft.bilingualb4.model.entity.AuthInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class JwtUtils {

    private final JwtConfig jwtConfig;

    public String generateToken(Authentication authentication) {
        AuthInfo authInfo = (AuthInfo) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(authInfo.getEmail())
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (jwtConfig.getExpirationDateAfterDays() * 8600000)))
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtConfig.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
