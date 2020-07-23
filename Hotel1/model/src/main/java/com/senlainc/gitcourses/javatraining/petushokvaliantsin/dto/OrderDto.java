package com.senlainc.gitcourses.javatraining.petushokvaliantsin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.OrderStatus;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto implements Serializable {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    @Positive(groups = Update.class)
    private Long id;
    @NotNull(groups = {Create.class, Update.class})
    private RoomDto room;
    @NotNull(groups = {Create.class, Update.class})
    private GuestDto guest;
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm+dd-MM-yyyy")
    private LocalDateTime orderDate;
    @NotNull(groups = Update.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AttendanceDto> attendances;
    @Future(groups = {Create.class})
    @NotNull(groups = {Create.class, Update.class})
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private OrderStatus status;
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    @PositiveOrZero(groups = {Create.class, Update.class})
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public GuestDto getGuest() {
        return guest;
    }

    public void setGuest(GuestDto guest) {
        this.guest = guest;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<AttendanceDto> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceDto> attendances) {
        this.attendances = attendances;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public interface Create {

    }

    public interface Update {

    }
}
