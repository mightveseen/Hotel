package com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.singularattribute.impl;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.IncorrectDataException;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.singularattribute.ISingularMapper;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper.singularattribute.annotations.SingularClasses;
import org.springframework.stereotype.Component;

import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

@Component
public class SingularMapper implements ISingularMapper {

    private final HashMap<String, Field> fields;

    public SingularMapper() {
        this.fields = new HashMap<>();
    }

    public void setClass(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(SingularClasses.class)) {
                final SingularClasses annotation = method.getAnnotation(SingularClasses.class);
                Arrays.asList(annotation.metaModels()).forEach(i -> {
                    for (Field field : i.getDeclaredFields()) {
                        if (field.getType().equals(SingularAttribute.class)) {
                            fields.put(field.getName().toLowerCase(), field);
                        }
                    }
                });
            }
        }
    }

    public <T, E> SingularAttribute<T, E> getSingularAttribute(String parameter) {
        try {
            return SingularAttribute.class.cast(fields.get(parameter.toLowerCase()).get(null));
        } catch (NullPointerException | IllegalAccessException e) {
            throw new IncorrectDataException("Chosen parameter [" + parameter + "] does not match any field");
        }
    }
}
