package com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.SystemUser;

public interface ISystemUserDao extends IGenericDao<SystemUser, Long> {

    SystemUser readByUsername(String username);
}
