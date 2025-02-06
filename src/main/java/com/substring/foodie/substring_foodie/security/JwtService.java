package com.substring.foodie.substring_foodie.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final long EXPIRATION_TIME = 15*60*1000;
    private static final String SECRET = "dscdjvckchkfxcvuyfvitdywfxcyuwvcwyucvwcvuydhcvctyifeqwbjvbjlnwejbhuvbaso";

    // generate token

    public String generateToken(String username) {
        String token =  Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                        .compact();
        return token;
    }

    //get username from token
    public String getUsername(String token) {
        String username = Jwts.parser()
                .setSigningKey(SECRET.getBytes()).build()
                .parseClaimsJws(token).getBody().getSubject();

        return username;
    }

    //validate token
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token);

            return true;
         }   catch(JwtException e){
            e.printStackTrace();
            return false;
        }
    }

}
