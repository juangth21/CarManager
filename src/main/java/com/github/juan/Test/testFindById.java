package com.github.juan.Test;

import com.github.juan.Model.dao.UserDAO;
import com.github.juan.Model.entity.User;

public class testFindById {

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        User userFound = userDAO.findByUsername("testUser");
        System.out.println("User found: " + userFound);

        User userNull = userDAO.findByUsername(null);
        System.out.println("User with null username: " + userNull);

        User userEmpty = userDAO.findByUsername("");
        System.out.println("User with empty username: " + userEmpty);
    }
    }

