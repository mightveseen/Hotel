package com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final TokenMapper tokenMapper;

    public AuthorizationFilter(AuthenticationManager authenticationManager, TokenMapper tokenMapper) {
        super(authenticationManager);
        this.tokenMapper = tokenMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String token = request.getHeader(tokenMapper.getTokenHeader());
        if (token == null || token.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        final UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        final UserDetails userDto = tokenMapper.parseToken(token);
        return new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword(),
                userDto.getAuthorities());
    }
}
