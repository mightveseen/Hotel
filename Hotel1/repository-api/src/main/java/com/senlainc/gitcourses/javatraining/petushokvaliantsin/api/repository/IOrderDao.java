package com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Order;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.Room;

import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;
import java.util.List;

public interface IOrderDao extends IGenericDao<Order, Long> {

    List<Room> readLastRoom(Long index, Integer limit);

    List<Room> readAfterDate(LocalDate date);

    <E, T> List<Order> readAll(int fistElement, int maxResult, SingularAttribute<Order, E> sortObject,
                               SingularAttribute<E, T> fieldSortObject);
}
