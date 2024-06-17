package com.github.juan.view;

import com.github.juan.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    private Controller centerController;

    @FXML
    static Alert alert= new Alert(Alert.AlertType.ERROR);

    @FXML
    static Alert alert2= new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    static Alert alertInfoRegister= new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void onOpen(Object input) throws IOException {
        changeScene(Scenes.LOGIN,null);
    }

    public void changeScene(Scenes scene,Object data) throws IOException {
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }

    public void openModal(Scenes scene, String title,Controller parent, Object data) throws IOException {
        View view = loadFXML(scene);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(parent);
        stage.showAndWait();
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene=p;
        view.controller=c;
        return view;
    }

    public static void ShowAlertsErrorLogin(){
        alert.setTitle("Error");
        alert.setContentText("Usuario o contraseña incorrectos");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorLoginPassword(){
        alert.setTitle("Error");
        alert.setContentText("La contraseña es incorrecta");
        alert.showAndWait();
    }
    public static void ShowAlertsInvalidEmail(){
        alert.setTitle("Error");
        alert.setContentText("El email tiene que ser valido");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorRegister(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo registrar el usuario");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorLogin2(){
        alert.setTitle("Error");
        alert.setContentText("Necesitas introducir los campos de usuario y contraseña");
        alert.showAndWait();

    }
    public static void ShowAlertsSuccessfullyRegister(){
        alert2.setTitle("Exito");
        alert2.setContentText("Usuario registrado con exito");
        alert2.showAndWait();
    }


    public static void ShowAlertsUserAlreadyExists(){
        alert.setTitle("Error");
        alert.setContentText("El usuario ya existe");
        alert.showAndWait();
    }
    public static void  ShowInformation(){
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setContentText("No puedes crear usuarios con el mismo nombre");
        alertInfoRegister.showAndWait();

    }

    public static void  ShowInformationUser(){
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para editar un garaje pulsa sobre el campo que desees");
        alertInfoRegister.showAndWait();

    }
    public static void  ShowInformationGarage(){
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para editar un garaje pulsa sobre el campo que desees");
        alertInfoRegister.showAndWait();
    }
    public static void  ShowInformationAddCars(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para añadir un coche a un garaje, selecciona el coche" +
                "y a que garaje deseas anadirlo. Para eliminarlo, pulse eliminar");
        alertInfoRegister.showAndWait();
    }
    public static void  ShowInformationCars(){
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para editar un coche, selecciona el campo que desees");
        alertInfoRegister.showAndWait();
    }

    public static void ShowAlertsGarageAlreadyExists(){
        alert.setTitle("Error");
        alert.setContentText("El garaje ya existe");
        alert.showAndWait();
    }
    public static void ShowAlertsCarsAlreadyExists(){
        alert.setTitle("Error");
        alert.setContentText("El coche ya existe");
        alert.showAndWait();
    }
    public static void ShowAlertsCarsAlreadyExistsInGarage(){
        alert.setTitle("Error");
        alert.setContentText("El coche ya esta en el garaje");
        alert.showAndWait();
    }
    public static void ShowAlertsSuccessfullyAddCarsToGarage(){
        alert2.setTitle("Exito");
        alert2.setContentText("Coche añadido con exito");
        alert2.showAndWait();
    }

    public static void ShowAlertsSuccessfullyCreateGarage(){
        alert2.setTitle("Exito");
        alert2.setContentText("Garage registrado con exito");
        alert2.showAndWait();
    }
    public static void ShowAlertsSuccessfullyCreateCars(){
        alert2.setTitle("Exito");
        alert2.setContentText("Coche registrado con exito");
        alert2.showAndWait();
    }

    public static void ShowAlertsErrorCreatingGarage(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo registrar el garage");
        alert.showAndWait();
    }


    public static void ShowAlertsErrorCreatingCars(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo registrar el coche");
        alert.showAndWait();
    }
    public static void  ShowInformationDeleteUser(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para borrar el usuario que deaseas introduce su username." +
                "Aqui no podras actualizar los datos de usuario");
        alertInfoRegister.showAndWait();

    }

    public static void ShowAlertsSuccessfullyDeleteUser(){
        alert2.setTitle("Exito");
        alert2.setContentText("Usuario eliminado con exito");
        alert2.showAndWait();
    }
    public static void ShowAlertsSuccessfullyDeleteGarage(){
        alert2.setTitle("Exito");
        alert2.setContentText("Garaje eliminado con exito");
        alert2.showAndWait();
    }
    public static void ShowAlertsSuccessfullyDeleteSpecies(){
        alert2.setTitle("Exito");
        alert2.setContentText("Coche eliminado con exito");
        alert2.showAndWait();
    }

    public static void ShowAlertsErrorDeleteUser(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo eliminar el usuario");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorDeleteGarage(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo eliminar el garaje");
        alert.showAndWait();
    }
    public static void ShowAlertsErrorDeleteCars(){
        alert.setTitle("Error");
        alert.setContentText("No se pudo eliminar el coche");
        alert.showAndWait();
    }

    public static void  ShowInformationDeleteGarage(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para borrar el garaje que deaseas introduce su id." +
                "Aqui no podras actualizar los datos del garaje");
        alertInfoRegister.showAndWait();

    }
    public static void ShowInformationDeleteCars(){
        alertInfoRegister.getDialogPane().setPrefHeight(200);
        alertInfoRegister.getDialogPane().setPrefWidth(300);
        alertInfoRegister.setHeaderText(null);
        alertInfoRegister.setTitle("Informacion");
        alertInfoRegister.setContentText("Para borrar el coche que deaseas introduce su id." +
                "Aqui no podras actualizar los datos del coche");
        alertInfoRegister.showAndWait();

    }

    @FXML
    private void closeApp(){
        System.exit(0);
    }


}

