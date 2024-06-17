package com.github.juan.Model.dao;

import com.github.juan.Model.connection.ConnectionMariaDB;
import com.github.juan.Model.entity.Cars;
import com.github.juan.Model.entity.CarsInGarage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarsInGarageDAO implements DAO<CarsInGarage, Integer> {
    private final static String ADDCARSINGARAGE = "INSERT INTO Stores (garageId, carsId) VALUES (?, ?)";
    private final static String DELETECARSFROMGARAGE = "DELETE FROM Stores WHERE garageId = ? AND carsId = ?";
    private final static String FINDINGARAGE = "SELECT COUNT(*) FROM Stores WHERE garageId = ? AND carsId = ?\n";
    private final static String FINDCARSBYID = "SELECT id, model, tuition, weight FROM Cars WHERE id = ?";
    private final static String FINDALLCARSINGARAGE = "SELECT c.id, c.model, c.tuition, c.weight FROM Cars AS c JOIN Stores AS s ON c.id = s.carsId WHERE s.garageId = ?";

    public void addCarsToGarage(int garageId, int carsId) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(ADDCARSINGARAGE)) {
            ps.setInt(1, garageId);
            ps.setInt(2, carsId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar coche en garaje", e);}
    }

    public boolean removeCarsFromGarage(int garageId, int carsId) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(DELETECARSFROMGARAGE)) {
            ps.setInt(1, garageId);
            ps.setInt(2, carsId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el coche del garaje", e);
        }
    }

    public List<Cars> findAllCarsInGarage(int garageId) {
        List<Cars> carsList = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDALLCARSINGARAGE)) {
            ps.setInt(1, garageId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String model = rs.getString("model");
                int tuition = rs.getInt("tuition");
                int weight = rs.getInt("weight");

                Cars cars = new Cars(id, model, tuition, weight);
                carsList.add(cars);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar coche en garaje", e);
        }

        return carsList;
    }


    @Override
    public CarsInGarage save(CarsInGarage entity) {
        return null;
    }

    @Override
    public CarsInGarage update(CarsInGarage entity) {
        return null;
    }

    @Override
    public CarsInGarage delete(CarsInGarage entity) throws SQLException {
        return null;
    }

    @Override
    public CarsInGarage findById(Integer key) {
        return null;
    }

    @Override
    public List<CarsInGarage> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    public static CarsInGarageDAO build() {
        return new CarsInGarageDAO();
    }

    public Cars findCarsById(int carsId) {
        Cars cars = null;
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDCARSBYID)) {
            ps.setInt(1, carsId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String model = rs.getString("model");
                int tuition = rs.getInt("tuition");
                int weight = rs.getInt("weight");

                cars = new Cars(id, model, tuition, weight);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar el coche por ID", e);
        }

        return cars;
    }

    public boolean isCarsInGarage(int garageId, int carsId) {
        Cars cars = findCarsById(carsId);

        if (cars != null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDINGARAGE)) {
                ps.setInt(1, garageId);
                ps.setInt(2, carsId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al verificar la existencia del coche en el garaje", e);
            }
        }

        return false;
    }
}
