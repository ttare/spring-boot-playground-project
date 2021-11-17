package com.example.imagesharingapi.config;

import com.example.imagesharingapi.models.dao.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenUtil {
    @Value("${jwtSecret}")
    private String jwtSecret;

    private static final int EXPIRATION = 60 * 24;


    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId() + "," + user.getEmail() + "," + user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(calculateExpiryDate())
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public boolean validate(String token) {
        try {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.out.println("JWT token validation exception" + e.getMessage());
        }
        return false;
    }

    public String getUserId(String token) {
        return getSubjectParts(token)[0];
    }

    public String getUserEmail(String token) {
        return getSubjectParts(token)[1];
    }

    public String getUsername(String token) {
        return getSubjectParts(token)[2];
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    private String[] getSubjectParts(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject()
                .split(",");
    }
}
