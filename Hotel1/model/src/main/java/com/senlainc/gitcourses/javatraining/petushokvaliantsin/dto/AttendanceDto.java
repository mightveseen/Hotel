package com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

public class AttendanceDto implements Serializable {

    @Null(groups = Create.class)
    @NotNull(groups = {Update.class, OrderDto.Create.class, OrderDto.Update.class})
    @Positive(groups = {Create.class, Update.class, OrderDto.Create.class, OrderDto.Update.class})
    private Long id;
    @NotNull(groups = {Create.class, Update.class})
    private String name;
    @NotNull(groups = {Create.class, Update.class})
    private String section;
    @NotNull(groups = {Create.class, Update.class})
    @PositiveOrZero(groups = {Create.class, Update.class})
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public interface Update {

    }

    public interface Create {

    }
}
