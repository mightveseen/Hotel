package com.senlainc.gitcourses.javatraining.petushokvaliantsin.service;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository.ISystemUserDao;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service.ISystemUserService;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.SystemUser;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class SystemUserService implements ISystemUserService, UserDetailsService {

    private final ISystemUserDao systemUserDao;

    @Autowired
    public SystemUserService(ISystemUserDao systemUserDao) {
        this.systemUserDao = systemUserDao;
    }

    @Override
    @Transactional
    public boolean create(SystemUser systemUser) {
        systemUser.setRole(UserRole.GUEST);
        systemUserDao.create(systemUser);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        final SystemUser user = systemUserDao.readByUsername(username);
        return new User(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
