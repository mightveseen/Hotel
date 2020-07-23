package com.senlainc.gitcourses.javatraining.petushokvaliantsin.model;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.RoomStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Rooms")
public class Room implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private int number;
    @Column
    private String classification;
    @Column(name = "room_number")
    private short roomNumber;
    @Column
    private short capacity;
    @Column
    @Enumerated(value = EnumType.STRING)
    private RoomStatus status;
    @Column
    private double price;
    @OneToMany(mappedBy = "room")
    private Set<Order> orders;

    public Room() {

    }

    public Room(int number, String classification, short roomNumber, short capacity, RoomStatus status, double price) {
        this.number = number;
        this.classification = classification;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.status = status;
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public short getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(short roomNumber) {
        this.roomNumber = roomNumber;
    }

    public short getCapacity() {
        return capacity;
    }

    public void setCapacity(short capacity) {
        this.capacity = capacity;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public Room clone() throws CloneNotSupportedException {
        return (Room) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                number == room.number &&
                roomNumber == room.roomNumber &&
                capacity == room.capacity &&
                Double.compare(room.price, price) == 0 &&
                Objects.equals(classification, room.classification) &&
                status == room.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, classification, roomNumber, capacity, status, price);
    }

    @Override
    public String toString() {
        return this.id + ")" + this.number + ", "
                + this.classification + ", " + this.roomNumber + ", "
                + this.capacity + ", " + this.status + ", " + this.price;
    }
}
