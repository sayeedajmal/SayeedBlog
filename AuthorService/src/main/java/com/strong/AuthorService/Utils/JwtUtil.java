package com.strong.AuthorService.Utils;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.strong.AuthorService.Entity.Author;
import com.strong.AuthorService.Repository.TokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * JwtUtil is a utility class for handling JWT (JSON Web Token) operations.
 * This class provides methods to generate, validate, and extract information
 * from JWTs.
 * It is used for authentication and authorization purposes within the
 * application.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.tokenValidityInSeconds}")
    private long accessTokenExpire;

    @Value("${jwt.tokenRefreshInSeconds}")
    private long refreshTokenExpire;

    @Autowired
    private TokenRepository tokenRepo;

    /**
     * Extracts the user email (subject) from the given JWT.
     * 
     * @param token the JWT from which the user email is to be extracted.
     * @return the extracted user email.
     */
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates the given JWT against the provided user details.
     * 
     * @param token  the JWT to validate.
     * @param author the user details to validate against.
     * @return true if the JWT is valid, false otherwise.
     */
    public boolean isValid(String token, UserDetails author) {
        String email = extractUserEmail(token);

        boolean validToken = tokenRepo
                .findByAccessToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (email.equals(author.getUsername())) && !isTokenExpired(token) && validToken;
    }

    /**
     * Validates the given refresh token against the provided author details.
     * 
     * @param token  the refresh token to validate.
     * @param author the author details to validate against.
     * @return true if the refresh token is valid, false otherwise.
     */
    public boolean isValidRefreshToken(String token, Author author) {
        String email = extractUserEmail(token);

        boolean validRefreshToken = tokenRepo
                .findByRefreshToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (email.equals(author.getUsername())) && !isTokenExpired(token) && validRefreshToken;
    }

    /**
     * Checks if the given JWT has expired.
     * 
     * @param token the JWT to check.
     * @return true if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the given JWT.
     * 
     * @param token the JWT from which the expiration date is to be extracted.
     * @return the extracted expiration date.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the given JWT.
     * 
     * @param token    the JWT from which the claim is to be extracted.
     * @param resolver the function to extract the specific claim.
     * @param <T>      the type of the claim.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT.
     * 
     * @param token the JWT from which the claims are to be extracted.
     * @return the extracted claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates an access token for the given author.
     * 
     * @param author the author for whom the access token is generated.
     * @return the generated access token.
     */
    public String generateAccessToken(Author author) {
        return generateToken(author, accessTokenExpire);
    }

    /**
     * Generates a refresh token for the given author.
     * 
     * @param author the author for whom the refresh token is generated.
     * @return the generated refresh token.
     */
    public String generateRefreshToken(Author author) {
        return generateToken(author, refreshTokenExpire);
    }

    /**
     * Generates a JWT token with the specified expiration time.
     * 
     * @param author     the author for whom the token is generated.
     * @param expireTime the expiration time of the token in milliseconds.
     * @return the generated JWT token.
     */
    private String generateToken(Author author, long expireTime) {
        String token = Jwts
                .builder()
                .subject(author.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    /**
     * Provides the signing key used for JWT operations.
     * 
     * @return the signing key.
     */
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
