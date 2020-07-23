package com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Attendance;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Order;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IOrderService extends IGenericService<Order, Long> {

    void addAttendance(long orderIndex, long attendanceIndex);

    List<Attendance> getAttendances(long orderIndex);

    List<Room> getGuestRooms(long index, int limit);

    List<Room> getRoomsAfterDate(LocalDate date, int firstElement, int maxResult);

    List<Order> readAll(int firstElement, int maxResult, String sortParameter);
}
