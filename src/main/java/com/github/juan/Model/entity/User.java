package com.github.juan.Model.entity;

import java.util.List;
import java.util.Objects;

public class User {

    private String username;
    private String name;
    private String password;
    private String email;
    private List<Garage> garages;

    public User(String username, String name, String password, String email, List<Garage> garages) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.garages = garages;
    }

    public User() {
        username = "";
        name = "";
        password = "";
        email = "";
        garages = null;
    }

    public User(String username,  String password) {
        this.username = username;
        this.password = password;
    }
    public User(String username, String name,  String password, String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Garage> getFishTanks() {
        return garages;
    }

    public void setFishTanks(List<Garage> garages) {
        this.garages = garages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);

    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

