package com.senlainc.gitcourses.javatraining.petushokvaliantsin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Guests")
public class Guest implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column
    private LocalDate birthday;
    @OneToMany(mappedBy = "guest")
    private Set<Order> orders;

    public Guest() {

    }

    public Guest(String firstName, String secondName, LocalDate birthday) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthday = birthday;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public Guest clone() throws CloneNotSupportedException {
        return (Guest) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return id == guest.id &&
                Objects.equals(firstName, guest.firstName) &&
                Objects.equals(secondName, guest.secondName) &&
                Objects.equals(birthday, guest.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, birthday);
    }

    @Override
    public String toString() {
        return this.id + ")" + this.firstName + ", "
                + this.secondName + ", " + this.birthday.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
