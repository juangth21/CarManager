package com.github.juan.Model.entity;

import java.util.Objects;

public class CarsInGarage {
    private int garageId;
    private int carsId;

    public CarsInGarage(int garageId, int carsId) {
        this.garageId = garageId;
        this.carsId = carsId;
    }
    public CarsInGarage(){

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarsInGarage that = (CarsInGarage) o;
        return garageId == that.garageId && carsId == that.carsId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(garageId, carsId);
    }

    @Override
    public String toString() {
        return "CarsInGarage{" +
                "carsId=" + carsId +
                ", carsId=" + carsId +
                '}';
    }
}
