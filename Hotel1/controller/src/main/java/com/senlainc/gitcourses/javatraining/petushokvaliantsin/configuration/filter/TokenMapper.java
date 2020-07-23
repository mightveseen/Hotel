package com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto.SystemUserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class TokenMapper {

    private final UserDetailsService userDetailsService;
    private final String tokenPrefix;
    private final String secretKey;
    private final String tokenHeader;

    public TokenMapper(UserDetailsService userDetailsService, String tokenPrefix, String secretKey, String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.tokenPrefix = tokenPrefix;
        this.secretKey = secretKey;
        this.tokenHeader = tokenHeader;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public UserDetails parseToken(String token) {
        try {
            final String decodedToken = JWT.require(HMAC512(secretKey.getBytes()))
                    .build()
                    .verify(token.substring(tokenPrefix.length() + 1))
                    .getSubject();
            return userDetailsService.loadUserByUsername(new ObjectMapper().readValue(decodedToken, SystemUserDto.class).getUsername());
        } catch (Exception exc) {
            return null;
        }
    }

    public String generateToken(Authentication authentication) {
        final User user = ((User) authentication.getPrincipal());
        return JWT.create()
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withSubject(getSubject(user))
                .withClaim("\"role\":\"", user.getAuthorities().toString())
                .sign(HMAC512(secretKey.getBytes()));
    }

    private String getSubject(User user) {
        return "{\"username\":\"" + user.getUsername() + "\"" + "}";
    }
}
