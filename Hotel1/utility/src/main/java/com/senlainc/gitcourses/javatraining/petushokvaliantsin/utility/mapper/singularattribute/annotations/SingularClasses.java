package com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.singularattribute.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SingularClasses {

    Class<?>[] metaModels();
}
