package com.github.juan.Model.dao;

import com.github.juan.Model.connection.ConnectionMariaDB;
import com.github.juan.Model.entity.Garage;
import com.github.juan.Model.entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User, String> {
    private final static String INSERT="INSERT INTO user (username,name,password,email) VALUES (?,?,?,?)";
    private final static String UPDATE="UPDATE user SET name = ?, password = ?, email = ? WHERE username = ?";
    private final static String FINDALL="SELECT u.username, u.name, u.password, u.email FROM user AS u";
    private final static String FINDBYUSERNAME="SELECT u.username,u.name, u.password, u.email FROM user AS u WHERE u.username = ?";
    private final static String DELETE="DELETE FROM user WHERE username = ?";

    @Override
    public User save(User user) {
        User result = new User();
        User u = findByUsername(user.getUsername());
        if (u == null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.executeUpdate();

                if (user.getFishTanks() != null) {
                    for (Garage garage : user.getFishTanks()) {
                        GarageDAO.build().save(garage);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar el usuario", e);
            }
        }
        return result;
    }

    @Override
    public User update(User user) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            User lastUser = findByUsername(user.getUsername());

            String nameToUpdate;
            if (user.getName() == null || user.getName().isEmpty()) {
                nameToUpdate = lastUser.getName();
            } else {
                nameToUpdate = user.getName();
            }
            ps.setString(1, nameToUpdate);

            String passwordToUpdate;
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                passwordToUpdate = lastUser.getPassword();
            } else {
                passwordToUpdate = user.getPassword();
            }
            ps.setString(2, passwordToUpdate);

            String emailToUpdate;
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                emailToUpdate = lastUser.getEmail();
            } else {
                emailToUpdate = user.getEmail();
            }
            ps.setString(3, emailToUpdate);

            ps.setString(4, user.getUsername());

            ps.executeUpdate();

            if (user.getFishTanks() != null) {
                List<Garage> fishTanksBefore = GarageDAO.build().findByUser(user.getUsername());
                List<Garage> fishTanksAfter = user.getFishTanks();

                List<Garage> fishTanksToBeDeleted = new ArrayList<>(fishTanksBefore);
                fishTanksToBeDeleted.removeAll(fishTanksAfter);

                for (Garage garage : fishTanksToBeDeleted) {
                    GarageDAO.build().delete(garage);
                }
                for (Garage garage : fishTanksAfter) {
                    GarageDAO.build().save(garage);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario", e);
        }

        return user;
    }

    @Override
    public User delete(User user) throws SQLException {
        if (user != null && user.getUsername() != null) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
                pst.setString(1, user.getUsername());
                pst.executeUpdate();
            }
        }
        return user;
    }

    @Override
    public User findById(String key) {
        return null;
    }

    public User findByUsername(String username) {
        User result = null;

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYUSERNAME)) {
                ps.setString(1, username);
                ResultSet res = ps.executeQuery();

                if (res.next()) {
                    result = new User();
                    result.setUsername(res.getString("username"));
                    result.setName(res.getString("name"));
                    result.setPassword(res.getString("password"));
                    result.setEmail(res.getString("email"));

                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public List<User> findAll() {
        List<User> result = new ArrayList<>();

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while(res.next()){
                User u = new User();
                u.setUsername(res.getString("username"));
                u.setName(res.getString("name"));
                u.setEmail(res.getString("email"));
                result.add(u);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean exists(String username) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYUSERNAME)) {
            ps.setString(1, username);
            ResultSet res = ps.executeQuery();

            return res.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() throws IOException {

    }
}
