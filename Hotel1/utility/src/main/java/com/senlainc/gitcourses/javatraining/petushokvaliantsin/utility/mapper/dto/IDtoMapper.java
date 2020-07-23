package com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.dto;

import java.util.List;

public interface IDtoMapper {

    <D, T> D map(T entity, Class<D> outClazz);

    <D, T> List<D> mapAll(List<T> entities, Class<D> outClazz);
}
