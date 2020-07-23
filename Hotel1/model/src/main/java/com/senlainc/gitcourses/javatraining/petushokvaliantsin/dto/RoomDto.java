package com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.RoomStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

public class RoomDto implements Serializable {

    @Null(groups = Create.class)
    @NotNull(groups = {Update.class, OrderDto.Create.class, OrderDto.Update.class})
    @Positive(groups = {Update.class, OrderDto.Create.class, OrderDto.Update.class})
    private Long id;
    @NotNull(groups = {Create.class, Update.class})
    private Integer number;
    @NotNull(groups = {Create.class, Update.class})
    private String classification;
    @NotNull(groups = {Create.class, Update.class})
    private Short roomNumber;
    @NotNull(groups = {Create.class, Update.class})
    private Short capacity;
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private RoomStatus status;
    @NotNull(groups = {Create.class, Update.class})
    @PositiveOrZero(groups = {Create.class, Update.class})
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Short getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Short roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Short getCapacity() {
        return capacity;
    }

    public void setCapacity(Short capacity) {
        this.capacity = capacity;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
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
