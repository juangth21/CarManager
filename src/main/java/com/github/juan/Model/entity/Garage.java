package com.github.juan.Model.entity;

import java.util.List;
import java.util.Objects;

public class Garage {
    private int id;
    private String location;
    private int capacity;
    private float lengthy;
    private float width;
    private float height;
    private List<Cars> cars;



    public Garage(int id, String location, int capacity, float lengthy, float width, float height, List<Cars> cars) {
        this.id = id;
        this.location = location;
        this.capacity = capacity;
        this.lengthy = lengthy;
        this.width = width;
        this.height = height;
        this.cars = cars;

    }
    public Garage(String location, int capacity, float lengthy, float width, float height) {
        this.location = location;
        this.capacity = capacity;
        this.lengthy = lengthy;
        this.width = width;
        this.height = height;

    }

    public Garage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getLengthy() {
        return lengthy;
    }

    public void setLengthy(float lengthy) {
        this.lengthy = lengthy;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public List<Cars> getCars() {
        return cars;
    }

    public void setCars(List<Cars> cars) {
        this.cars = cars;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Garage fishtank = (Garage) o;
        return id == fishtank.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Fishtank{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", lengthy=" + lengthy +
                ", width=" + width +
                ", height=" + height +
                ", cars=" + cars +
                '}';
    }
}
