package com.github.juan.view;

public enum Scenes {
    LAYOUT("layout.fxml"),
    MAIN("Main.fxml"),
    REGISTER("Register.fxml"),
    CARSCONFIG("CarsConfig.fxml"),
    GARAGECONFIG("GarageConfig.fxml"),
    USERCONFIG("UserConfig.fxml"),
    CREATEGARAGE("CreateGarage.fxml"),
    DELETEGARAGE("DeleteGarage.fxml"),
    DELETEUSER("DeleteUser.fxml"),
    CREATECARS("CreateCars.fxml"),
    DELETECARS("DeleteCars.fxml"),
    ADDSCARSINGARAGE("AddCarsInGarage.fxml"),
    LOGIN("Login.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }
}
