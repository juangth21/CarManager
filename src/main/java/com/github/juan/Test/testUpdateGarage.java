package com.github.juan.Test;

import com.github.juan.Model.dao.GarageDAO;
import com.github.juan.Model.entity.Garage;


public class testUpdateGarage {
    public static void main(String[] args) {
        Garage g = new Garage();
        g.setId(122);
        g.setLocation("Updated");
        g.setCapacity(20);
        g.setLengthy(6);
        g.setWidth(3);
        g.setHeight(3);
        GarageDAO garageDAO = new GarageDAO();
        Garage updatedGarage = garageDAO.update(g);
        if (updatedGarage != null) {
            System.out.println("Garaje actualizado:");
            System.out.println("Location: " + updatedGarage.getLocation());
            System.out.println("Capacity: " + updatedGarage.getCapacity());
            System.out.println("Lengthy: " + updatedGarage.getLengthy());
            System.out.println("Width: " + updatedGarage.getWidth());
            System.out.println("Height: " + updatedGarage.getHeight());
        } else {
            System.out.println("Error en la actualizacion");
        }
    }
}
