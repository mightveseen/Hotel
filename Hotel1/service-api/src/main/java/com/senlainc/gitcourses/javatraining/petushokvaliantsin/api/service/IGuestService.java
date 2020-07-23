package com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Guest;

public interface IGuestService extends IGenericService<Guest, Long> {

    Long getNum();
}
