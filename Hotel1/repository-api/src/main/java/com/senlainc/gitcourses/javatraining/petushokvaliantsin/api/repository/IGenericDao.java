package com.senlainc.gitcourses.javatraining.petushokvaliantsin.api.repository;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;

public interface IGenericDao<E, K extends Serializable> {

    void create(E object);

    void delete(E object);

    void update(E object);

    Long readSize();

    E read(K index);

    List<E> readAll(int fistElement, int maxResult);

    <T> List<E> readAll(int firstElement, int maxResult, SingularAttribute<E, T> sortParameter);
}
