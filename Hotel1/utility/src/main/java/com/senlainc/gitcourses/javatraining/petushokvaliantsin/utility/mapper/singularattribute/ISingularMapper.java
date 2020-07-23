package com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.singularattribute;

import javax.persistence.metamodel.SingularAttribute;

public interface ISingularMapper {

    void setClass(Class<?> clazz);

    <T, E> SingularAttribute<T, E> getSingularAttribute(String parameter);
}
