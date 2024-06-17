package com.github.juan.Test;

import com.github.juan.Model.dao.UserDAO;
import com.github.juan.Model.entity.User;

public class testInsert {
    public static void main(String[] args) {
        User u  = new User("juan", "juan", "juan", "juan@gmail.com", null);
        User u1 = new User("juan1", "juan", "juan1", "juan@gmail.com", null);
        User u2 = new User("juan2", "juan", "juan2", "user2@example.com", null);
        UserDAO userDAO = new UserDAO();
        userDAO.save(u1);
        userDAO.save(u2);
        userDAO.save(u);


    }
}
