package com.github.juan.view;

import com.github.juan.Model.Singleton.UserSession;
import com.github.juan.Model.dao.CarsDAO;
import com.github.juan.Model.entity.Cars;
import com.github.juan.Model.entity.User;
import com.github.juan.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateCarsController extends Controller implements Initializable {
    @FXML
    TextField model;
    @FXML
    TextField tuition;
    @FXML
    TextField weight;
    @FXML
    Button crear;

    @Override
    public void onOpen(Object input) throws IOException {
        if (input instanceof User) {
            UserSession.login((User) input);
        }
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Cars getSpeciesValues() {
        User currentUser = UserSession.getCurrentUser();
        Cars cars = null;

        if(currentUser != null) {
            String models = model.getText();
            int tuitions = Integer.parseInt(tuition.getText());
            int weights = Integer.parseInt(weight.getText());

            cars = new Cars(models, tuitions, weights);
        }

        return cars;
    }

    public void createCars() throws IOException {
        Cars cars = getSpeciesValues();

        if (cars == null || cars.getModel().isEmpty() || cars.getTuition() == 0 || cars.getWeight() == 0) {
            AppController.ShowAlertsErrorCreatingCars();
        } else {
            CarsDAO carsDAO = new CarsDAO();

            if (carsDAO.exists(cars.getId())) {
                AppController.ShowAlertsCarsAlreadyExists();
            } else {
                if (carsDAO.saveCars(cars, UserSession.getCurrentUser()) != null) {
                    AppController.ShowAlertsSuccessfullyCreateCars();
                } else {
                    AppController.ShowAlertsErrorCreatingCars();
                }
            }
        }
    }


    public void changeSceneToCarsConfig() throws IOException{
        App.currentController.changeScene(Scenes.CARSCONFIG,null);
    }
}

