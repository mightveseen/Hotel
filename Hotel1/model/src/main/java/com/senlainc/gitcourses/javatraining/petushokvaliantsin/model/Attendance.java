package com.senlainc.gitcourses.javatraining.petushokvaliantsin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Attendances")
public class Attendance implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String section;
    @Column
    private double price;

    public Attendance() {

    }

    public Attendance(String name, String section, double price) {
        this.name = name;
        this.section = section;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public Attendance clone() throws CloneNotSupportedException {
        return (Attendance) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(section, that.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, section, price);
    }

    @Override
    public String toString() {
        return this.id + ")" + this.name + ", " + this.section + ", " + this.price;
    }
}