package com.senlainc.gitcourses.javatraining.petushokvaliantsin.repository;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository.IGuestDao;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Guest;
import org.springframework.stereotype.Repository;

@Repository
public class GuestDao extends AbstractDao<Guest, Long> implements IGuestDao {

}
