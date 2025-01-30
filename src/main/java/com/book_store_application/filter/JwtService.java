package com.book_store_application.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.*;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    // Extract the username (subject) from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract any claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Generate the token with extra claims (including user ID)
    public String generateToken(UserDetails userDetails, Long userId) {
        return generateToken(new HashMap<>(), userDetails, userId);  // Pass the userId to the method
    }

    // Generate the token with extra claims and user ID
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            Long userId
    ) {
        return buildToken(extraClaims, userDetails, userId, jwtExpiration); // Pass userId to buildToken
    }

    // Generate a refresh token
    public String generateRefreshToken(UserDetails userDetails, Long userId) {
        return buildToken(new HashMap<>(), userDetails, userId, refreshExpiration); // Pass userId to buildToken
    }

    // Build the token, including user ID and claims
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            Long userId,
            long expiration
    ) {
        extraClaims.put("user_id",userId);  // Add user_id claim

        return Jwts
                .builder()
                .claims()
                .add(extraClaims)
                .subject(userDetails.getUsername())  // Set username as subject
                .issuedAt(new Date(System.currentTimeMillis()))  // Set issued at timestamp
                .expiration(new Date(System.currentTimeMillis() + expiration))  // Set expiration timestamp
                .and()
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)  // Sign the token
                .compact();
    }

    // Validate the token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get the signing key (from the secret key)
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract the user ID from the token
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> {
            Object userId = claims.get("user_id");
            if (userId != null) {
                return Long.valueOf(userId.toString());
            }
            throw new IllegalArgumentException("User ID claim is missing in the token");
        });
    }
}
