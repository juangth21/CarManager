package com.github.juan.Model.dao;

import com.github.juan.Model.connection.ConnectionMariaDB;
import com.github.juan.Model.entity.Cars;
import com.github.juan.Model.entity.Garage;
import com.github.juan.Model.entity.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarsDAO implements DAO<Cars, Integer> {
    private final static String INSERT="INSERT INTO cars (id,model,tuition,weight) VALUES (?,?,?,?)";
    private final static String UPDATE="UPDATE cars SET model = ?, tuition = ?, weight = ? WHERE id = ?";
    private final static String FINDALL="SELECT c.id, c.model, c.tuition, c.weight FROM cars AS c";
    private final static String FINDBYID="SELECT c.id, c.model, c.tuition, c.weight FROM cars AS c WHERE c.id = ?";
    private final static String FINDBYMODEL ="SELECT c.id, c.model, c.tuition, c.weight FROM cars AS c WHERE c.model = ?";
    private final static String DELETE="DELETE FROM cars WHERE id = ?";
    private final static String FINDINGARAGE ="SELECT c.id, c.model, c.tuition, c.weight FROM cars AS c WHERE c.id = ?";

    @Override
    public Cars save(Cars cars) {
        return null;
    }

    public Cars saveCars(Cars cars, User currentUser) {
        Cars result = new Cars();
        Cars s = findById(cars.getId());
        if (s == null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setInt(1, cars.getId());
                ps.setString(2, cars.getModel());
                ps.setInt(3, cars.getTuition());
                ps.setInt(4, cars.getWeight());
                ps.setString(5, currentUser.getUsername());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar el coche", e);
            }
        }
        return result;
    }

    @Override
    public Cars update(Cars cars) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                ps.setString(1, cars.getModel());
                ps.setInt(2, cars.getTuition());
                ps.setInt(3, cars.getWeight());
                ps.setInt(4, cars.getId());

                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar coche", e);
            }
            return cars;
        }

        @Override
    public Cars delete(Cars cars) throws SQLException {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            ps.setInt(1, cars.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar coche", e);
        }
        return cars;
    }

    @Override
    public Cars findById(Integer id) {
        Cars cars = null;

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cars = new Cars();
                    cars.setId(rs.getInt("id"));
                    cars.setModel(rs.getString("model"));
                    cars.setTuition(rs.getInt("tuition"));
                    cars.setWeight(rs.getInt("weight"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar el coche por ID", e);
        }

        return cars;
    }

    @Override
    public List<Cars> findAll() {
        List<Cars> result = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cars cars = new Cars();
                cars.setId(rs.getInt("id"));
                cars.setModel(rs.getString("model"));
                cars.setTuition(rs.getInt("tuition"));
                cars.setWeight(rs.getInt("weight"));

                result.add(cars);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar todos los coches", e);
        }

        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public List<Cars> findInGarage(Garage garage) {
        List<Cars> carsInGarage = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDINGARAGE)) {
            ps.setInt(1, garage.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cars cars = new Cars();
                    carsInGarage.add(cars);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar coches en garaje", e);
        }

        return carsInGarage;
    }

    public Cars findCarsByModel(String model) {
        Cars result = null;
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYMODEL)) {
            ps.setString(1, model);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cars c = new Cars();
                    c.setId(rs.getInt("id"));
                    c.setModel(rs.getString("model"));
                    c.setTuition(rs.getInt("tuition"));
                    c.setWeight(rs.getInt("weight"));
                    result = c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
    public static CarsDAO build(){
        return new CarsDAO();
    }



}
