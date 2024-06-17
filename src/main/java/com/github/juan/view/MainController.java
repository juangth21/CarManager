package com.github.juan.view;

import com.github.juan.Model.Singleton.UserSession;
import com.github.juan.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
    @FXML
    Button users;

    @FXML
    Button garages;

    @FXML
    Button cars;

    @FXML
    Button logout;

    @FXML
    ImageView boton;

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Logout() throws IOException{
        UserSession.logout();
        App.currentController.changeScene(Scenes.LOGIN,null);
    }

    public void changeSceneToUsersConfig() throws IOException{
        App.currentController.changeScene(Scenes.USERCONFIG,null);
    }
    public void changeSceneToGaragesConfig() throws IOException{
        App.currentController.changeScene(Scenes.GARAGECONFIG,null);
    }
    public void changeSceneToCarsConfig() throws IOException{
        App.currentController.changeScene(Scenes.CARSCONFIG,null);
    }








}
