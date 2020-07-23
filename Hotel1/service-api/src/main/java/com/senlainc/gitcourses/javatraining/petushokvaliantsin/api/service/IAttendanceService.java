package com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Attendance;

import java.util.List;

public interface IAttendanceService extends IGenericService<Attendance, Long> {

    void changePrice(long index, double price);

    List<Attendance> readAll(int firstElement, int maxResult, String sortParameter);
}
