package com.substring.foodie.substring_foodie.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final long EXPIRATION_TIME = 15 * 60 * 1000; // 15 minutes
    private static final String SECRET = "dscdjvckchkfxcvuyfvitdywfxcyuwvcwyucvwcvuydhcvctyifeqwbjvbjlnwejbhuvbaso";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    // Generate JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())  // No need to specify algorithm explicitly
                .compact();
    }

    // Extract Username from JWT Token
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Verifies signature with the key
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validate JWT Token
    public boolean validateToken(String token) {

        if (this.isTokenExpired(token)){
               return false;
        }

        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}