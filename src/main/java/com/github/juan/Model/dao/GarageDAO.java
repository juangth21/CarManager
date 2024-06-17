package com.github.juan.Model.dao;

import com.github.juan.Model.connection.ConnectionMariaDB;
import com.github.juan.Model.entity.Cars;
import com.github.juan.Model.entity.Garage;
import com.github.juan.Model.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GarageDAO implements DAO<Garage, Integer> {
    private final static String INSERT="INSERT INTO Garage (location, capacity, lengthy, width, height, user_username) VALUES (?, ?, ?, ?, ?, ?);";
    private final static String UPDATE = "UPDATE Garage SET location = ?, capacity = ?, lengthy = ?, width = ?, height = ? WHERE id = ?";
    private final static String FINDALL = "SELECT g.id, g.location, g.capacity, g.lengthy, g.width, g.height FROM Garage AS g";
    private final static String FINDBYID = "SELECT g.id, g.location, g.capacity, g.lengthy, g.width, g.height FROM Garage AS g WHERE g.id = ?";
    private final static String DELETE = "DELETE FROM Garage WHERE id = ?";
    private final static String FINDBYUSER = "SELECT * FROM garage WHERE username = ?";

    public Garage saveGarage(Garage garage, User currentUser) {
        Garage result = new Garage();
        Garage f = findById(garage.getId());
        if (f == null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setString(1, garage.getLocation());
                ps.setInt(2, garage.getCapacity());
                ps.setFloat(3, garage.getLengthy());
                ps.setFloat(4, garage.getWidth());
                ps.setFloat(5, garage.getHeight());
                ps.setString(6, currentUser.getUsername());
                ps.executeUpdate();

                if (garage.getCars() != null) {
                    for (Cars cars : garage.getCars()) {
                        CarsDAO.build().save(cars);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar el usuario", e);
            }
        }
        return result;
    }

    @Override
    public Garage save(Garage entity) {
        return null;
    }

    @Override
    public Garage update(Garage garage) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            ps.setString(1, garage.getLocation());
            ps.setInt(2, garage.getCapacity());
            ps.setFloat(3, garage.getLengthy());
            ps.setFloat(4, garage.getWidth());
            ps.setFloat(5, garage.getHeight());
            ps.setInt(6, garage.getId());

            ps.executeUpdate();

            if(garage.getCars() != null) {
                List<Cars> carsBefore = CarsDAO.build().findInGarage(garage);
                List<Cars> carsAfter = garage.getCars();

                List<Cars> carsToBeRemoved = new ArrayList<>(carsBefore);
                carsToBeRemoved.removeAll(carsAfter);

                for(Cars c : carsToBeRemoved) {
                    CarsDAO.build().delete(c);
                }
                for(Cars c : carsAfter) {
                    CarsDAO.build().save(c);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario", e);
        }

        return garage;
    }

    @Override
    public Garage delete(Garage garage) throws SQLException {
        if ( garage != null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
                ps.setInt(1, garage.getId());
                ps.executeUpdate();
            }
        }
        return garage;
    }

    @Override
    public Garage findById(Integer id) {
        Garage result = null;
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            ps.setString(1, id.toString());
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                result = new Garage();
                result.setId(res.getInt("id"));
                result.setLocation(res.getString("location"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Garage> findAll() {
        List<Garage> result = new ArrayList<>();

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while(res.next()){
                Garage g = new Garage();
                g.setId(res.getInt("id"));
                g.setLocation(res.getString("location"));
                g.setCapacity(res.getInt("capacity"));
                g.setLengthy(res.getFloat("lengthy"));
                g.setWidth(res.getFloat("width"));
                g.setHeight(res.getFloat("height"));
                result.add(g);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Garage> findByUser(String username) {
        List<Garage> garages = new ArrayList<>();

        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(FINDBYUSER)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Garage garage = new Garage();
                garage.setId(rs.getInt("id"));
                garage.setLocation(rs.getString("location"));

                garages.add(garage);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el garage del usuario", e);
        }

        return garages;
    }

    @Override
    public void close() throws IOException {

    }

    public static GarageDAO build(){
        return new GarageDAO();
    }

    public boolean exists(Integer id) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            ps.setString(1, id.toString());
            ResultSet res = ps.executeQuery();
            return res.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


