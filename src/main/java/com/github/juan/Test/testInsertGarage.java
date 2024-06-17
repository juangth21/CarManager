package com.github.juan.Test;


import com.github.juan.Model.dao.GarageDAO;
import com.github.juan.Model.entity.Garage;

public class testInsertGarage {

    public static void main(String[] args) {
        Garage g  = new Garage(111,"madrid",20,6,3,3,null);
        Garage g1 = new Garage(222,"barcelona",20,6,3,3,null);
        Garage g2 = new Garage(333,"malaga",20,6,3,3,null);
        GarageDAO garageDAO = new GarageDAO();
        garageDAO.save(g);
        garageDAO.save(g1);
        garageDAO.save(g2);

    }
}
