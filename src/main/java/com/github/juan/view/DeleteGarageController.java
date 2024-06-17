package com.github.juan.view;

import com.github.juan.Model.dao.GarageDAO;
import com.github.juan.Model.entity.Garage;
import com.github.juan.App;
import javafx.beans.property.SimpleFloatProperty;
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

public class DeleteGarageController extends Controller implements Initializable {
    @FXML
    ImageView volver;
    @FXML
    TableView<Garage> table;
    @FXML
    TableColumn<Garage, Integer> Id;
    @FXML
    TableColumn<Garage, String> location;
    @FXML
    TableColumn<Garage, String> capacity;
    @FXML
    TableColumn<Garage, String> lengthy;
    @FXML
    TableColumn<Garage, String> width;
    @FXML
    TableColumn<Garage, String> height;
    @FXML
    Button eliminar;
    @FXML
    TextField ids;

    private ObservableList<Garage> garages;

    @Override
    public void onOpen(Object input) throws IOException {
        GarageDAO fDAO = new GarageDAO();
        List<Garage> garages = fDAO.findAll();
        this.garages = FXCollections.observableArrayList(garages);
        table.setItems(this.garages);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GarageDAO gDAO = new GarageDAO();
        table.setEditable(true);
        Id.setCellValueFactory(garage -> new SimpleIntegerProperty(garage.getValue().getId()).asObject());
        location.setCellValueFactory(garage -> new SimpleStringProperty(garage.getValue().getLocation()));
        capacity.setCellValueFactory(garage -> new SimpleIntegerProperty(garage.getValue().getCapacity()).asString());
        lengthy.setCellValueFactory(garage -> new SimpleFloatProperty(garage.getValue().getLengthy()).asString());
        width.setCellValueFactory(garage -> new SimpleFloatProperty(garage.getValue().getWidth()).asString());
        height.setCellValueFactory(garage -> new SimpleFloatProperty(garage.getValue().getHeight()).asString());
        Id.getTableView().refresh();

    }

    public Garage getValuesTextField() {
        Garage garagedao = new Garage();
        int id = Integer.parseInt(ids.getText());
        garagedao.setId(id);
        return garagedao;
    }

    public void deleteGarage() throws SQLException, IOException {
        Garage garagedao = getValuesTextField();
        GarageDAO gDAO = new GarageDAO();
        if (gDAO.findById(garagedao.getId()) != null) {
            gDAO.delete(garagedao);
            AppController.ShowAlertsSuccessfullyDeleteGarage();
            App.currentController.changeScene(Scenes.DELETEGARAGE,null);
        } else {
            AppController.ShowAlertsErrorDeleteGarage();
        }
    }

    public void changeSceneToGarageConfig() throws IOException{
        App.currentController.changeScene(Scenes.GARAGECONFIG,null);
    }
}
