package com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

public class GuestDto implements Serializable {

    @Null(groups = Create.class)
    @NotNull(groups = {Update.class, OrderDto.Create.class, OrderDto.Update.class})
    @Positive(groups = {Update.class, OrderDto.Create.class, OrderDto.Update.class})
    private Long id;
    @NotNull(groups = {Create.class, Update.class})
    private String firstName;
    @NotNull(groups = {Create.class, Update.class})
    private String secondName;
    @Past(groups = {Create.class})
    @NotNull(groups = {Create.class, Update.class})
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public interface Update {

    }

    public interface Create {

    }
}
