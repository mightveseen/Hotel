package com.senlainc.gitcourses.javatraining.petushokvaliantsin.controller;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.ISystemUserService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.filter.TokenMapper;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto.SystemUserDto;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.SystemUser;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.dto.IDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MainController {

    private final PasswordEncoder passwordEncoder;
    private final IDtoMapper dtoMapper;
    private final ISystemUserService systemUserService;
    private final AuthenticationManager authenticationManager;
    private final TokenMapper tokenMapper;

    @Autowired
    public MainController(PasswordEncoder passwordEncoder, IDtoMapper dtoMapper, ISystemUserService systemUserService,
                          AuthenticationManager authenticationManager, TokenMapper tokenMapper) {
        this.passwordEncoder = passwordEncoder;
        this.dtoMapper = dtoMapper;
        this.systemUserService = systemUserService;
        this.authenticationManager = authenticationManager;
        this.tokenMapper = tokenMapper;
    }

    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody @Validated(SystemUserDto.Create.class) SystemUserDto systemUserDto) {
        systemUserDto.setPassword(passwordEncoder.encode(systemUserDto.getPassword()));
        systemUserService.create(dtoMapper.map(systemUserDto, SystemUser.class));
    }

    @GetMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String authenticateUser(@RequestBody @Validated(SystemUserDto.Login.class) SystemUserDto systemUserDto) {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(systemUserDto.getUsername(), systemUserDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(token));
        return tokenMapper.generateToken(SecurityContextHolder.getContext().getAuthentication());
    }
}
