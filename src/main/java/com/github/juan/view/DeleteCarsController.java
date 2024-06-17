package com.github.juan.view;

import com.github.juan.Model.dao.CarsDAO;
import com.github.juan.Model.entity.Cars;
import com.github.juan.App;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteCarsController extends Controller implements Initializable {
    @FXML
    ImageView volver;
    @FXML
    TableView<Cars> table;
    @FXML
    TableColumn<Cars, Integer> Id;
    @FXML
    TableColumn<Cars, String> model;
    @FXML
    TableColumn<Cars, Integer> tuition;
    @FXML
    TableColumn<Cars, Integer> weight;
    @FXML
    Button eliminar;
    @FXML
    TextField ids;

    private ObservableList<Cars> cars;

    @Override
    public void onOpen(Object input) throws IOException {
        CarsDAO cDAO = new CarsDAO();
        List<Cars> cars = cDAO.findAll();
        this.cars = FXCollections.observableArrayList(cars);
        table.setItems(this.cars);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CarsDAO cDAO = new CarsDAO();
        table.setEditable(true);
        Id.setCellValueFactory(cars -> new SimpleIntegerProperty(cars.getValue().getId()).asObject());
        model.setCellValueFactory(cars -> new SimpleStringProperty(cars.getValue().getModel()));
        tuition.setCellValueFactory(cars -> new SimpleIntegerProperty(cars.getValue().getTuition()).asObject());
        weight.setCellValueFactory(cars -> new SimpleIntegerProperty(cars.getValue().getWeight()).asObject());
    }

    public Cars getValuesTextField() {
        Cars carsDAO = new Cars();
        int id = Integer.parseInt(ids.getText());
        carsDAO.setId(id);
        return carsDAO;
    }

    public void deleteCars() throws SQLException, IOException {
        Cars carsDAO = getValuesTextField();
        CarsDAO cDAO = new CarsDAO();
        if (cDAO.findById(carsDAO.getId()) != null) {
            cDAO.delete(carsDAO);
            AppController.ShowAlertsSuccessfullyDeleteSpecies();
            App.currentController.changeScene(Scenes.DELETECARS,null);
        } else {
            AppController.ShowAlertsErrorDeleteCars();
        }
    }

    public void changeSceneToCarsConfig() throws IOException{
        App.currentController.changeScene(Scenes.CARSCONFIG,null);
    }
}


