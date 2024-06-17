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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CarsConfigController extends Controller implements Initializable {
    @FXML
    Button crear;
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
    Button anadir;

    private ObservableList<Cars> cars;

    @Override
    public void onOpen(Object input) throws IOException {
        CarsDAO sDAO = new CarsDAO();
        List<Cars> cars = sDAO.findAll();
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
        model.setCellFactory(TextFieldTableCell.forTableColumn());
        tuition.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        weight.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        model.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }
            Cars cars = event.getRowValue();
            cars.setModel(event.getNewValue());
            cDAO.update(cars);
        });
        tuition.setOnEditCommit(event2 -> {
            if (event2.getNewValue().equals(event2.getOldValue())) {
                return;
            }
            Cars cars = event2.getRowValue();
            cars.setTuition(Integer.parseInt(String.valueOf(event2.getNewValue())));
            cDAO.update(cars);
        });
        weight.setOnEditCommit(event3 -> {
            if (event3.getNewValue().equals(event3.getOldValue())) {
                return;
            }
            Cars cars = event3.getRowValue();
            cars.setWeight(Integer.parseInt(String.valueOf(event3.getNewValue())));
            cDAO.update(cars);
        });
    }

    public void changeSceneToMainPage() throws IOException{
        App.currentController.changeScene(Scenes.MAIN,null);
    }
    public void changeSceneToCreateCars() throws IOException{
        App.currentController.changeScene(Scenes.CREATECARS,null);
    }
    public void changeSceneToDeleteCars() throws IOException{
        App.currentController.changeScene(Scenes.DELETECARS,null);
    }
    public void changeSceneToAddCarsGarage() throws IOException{
        App.currentController.changeScene(Scenes.ADDSCARSINGARAGE,null);
    }

}