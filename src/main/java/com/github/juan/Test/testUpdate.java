package com.github.juan.Test;

import com.github.juan.Model.dao.UserDAO;
import com.github.juan.Model.entity.User;

public class testUpdate {
    public static void main(String[] args) {
        User userToUpdate = new User();
        userToUpdate.setUsername("testUser");
        userToUpdate.setName("Updated Name");
        userToUpdate.setPassword("updated");
        userToUpdate.setEmail("updated@example.com");
        UserDAO userDAO = new UserDAO();
        User updatedUser = userDAO.update(userToUpdate);
        if (updatedUser != null) {
            System.out.println("Usuario actualizado correctamente:");
            System.out.println("Name: " + updatedUser.getName());
            System.out.println("Password: " + updatedUser.getPassword());
            System.out.println("Email: " + updatedUser.getEmail());
        } else {
            System.out.println("Error al actualizar");
        }
    }
}