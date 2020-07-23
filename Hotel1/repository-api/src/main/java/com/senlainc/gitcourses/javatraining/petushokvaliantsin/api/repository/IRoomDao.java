package com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.RoomStatus;

import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

public interface IRoomDao extends IGenericDao<Room, Long> {

    Long readFreeSize();

    RoomStatus readStatus(long index);

    Double readPrice(long index);

    boolean readByNumber(int number);

    <T> List<Room> readAll(SingularAttribute<Room, T> field, T criteria, int fistElement, int maxResult);

    <E, T> List<Room> readAll(SingularAttribute<Room, E> field, E criteria, int fistElement, int maxResult, SingularAttribute<Room, T> sortParameter);
}
