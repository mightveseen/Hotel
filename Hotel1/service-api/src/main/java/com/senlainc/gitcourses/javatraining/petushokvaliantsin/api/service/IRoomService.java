package com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.service;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.RoomStatus;

import java.util.List;

public interface IRoomService extends IGenericService<Room, Long> {

    void changeStatus(long index, String status);

    Long getNumFree();

    RoomStatus getRoomStatus(long index);

    Double gerRoomPrice(long index);

    List<Room> readAll(String criteria, int firstElement, int maxResult);

    List<Room> readAll(String criteria, int firstElement, int maxResult, String sortParameter);
}
