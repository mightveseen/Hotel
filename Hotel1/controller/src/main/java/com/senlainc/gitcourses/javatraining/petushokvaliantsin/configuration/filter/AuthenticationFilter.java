package com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto.SystemUserDto;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.ElementNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenMapper tokenMapper;

    public AuthenticationFilter(AuthenticationManager authenticationManager, TokenMapper tokenMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenMapper = tokenMapper;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        final String token = tokenMapper.generateToken(authResult);
        response.setHeader(tokenMapper.getTokenHeader(), token);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            final StringBuilder requestBody = new StringBuilder();
            request.getReader().lines().collect(Collectors.toList()).forEach(requestBody::append);
            final SystemUserDto userDto = new ObjectMapper().readValue(requestBody.toString(), SystemUserDto.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        } catch (IOException e) {
            throw new ElementNotFoundException(e.getMessage());
        }
    }
}
