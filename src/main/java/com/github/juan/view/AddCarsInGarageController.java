package com.github.juan.view;

import com.github.juan.Model.dao.CarsDAO;
import com.github.juan.Model.dao.CarsInGarageDAO;
import com.github.juan.Model.dao.GarageDAO;
import com.github.juan.Model.entity.Cars;
import com.github.juan.Model.entity.Garage;
import com.github.juan.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddCarsInGarageController extends Controller implements Initializable {
    @FXML
    private ComboBox<String> GaragesComboBox;
    @FXML
    private ComboBox<String> CarsComboBox;
    @FXML
    private ComboBox<String> DeleteCarsComboBox;
    @FXML
    private TableView<Cars> CarsTableView;
    @FXML
    private TableColumn<Cars, String> CarsModelColumn;
    @FXML
    private TableColumn<Cars, Integer> CarsTuitionColumn;
    @FXML
    private TableColumn<Cars, Integer> CarsWeightColumn;


    private int currentGarageId;
    private Map<String, Integer> garageLocationToIdMap;

    @Override
    public void onOpen(Object input) throws IOException {
        if (input instanceof Integer) {
            currentGarageId = (Integer) input;
            loadCarsInGarage(currentGarageId);
        }
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Garage> garages = GarageDAO.build().findAll();

        garageLocationToIdMap = new HashMap<>();
        for (Garage tank : garages) {
            garageLocationToIdMap.put(tank.getLocation(), tank.getId());
        }

        List<String> garageLocations = garages.stream().map(Garage::getLocation).collect(Collectors.toList());
        GaragesComboBox.setItems(FXCollections.observableArrayList(garageLocations));

        List<Cars> cars = CarsDAO.build().findAll();
        List<String> carsModels = cars.stream().map(Cars::getModel).collect(Collectors.toList());
        CarsComboBox.setItems(FXCollections.observableArrayList(carsModels));

        GaragesComboBox.setOnAction(event -> {
            String selectedGarageLocation = GaragesComboBox.getValue();
            if (selectedGarageLocation != null) {
                try {
                    int selectedGarageId = garageLocationToIdMap.get(selectedGarageLocation);
                    onOpen(selectedGarageId);
                    loadCarsInGarage(selectedGarageId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        CarsModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        CarsTuitionColumn.setCellValueFactory(new PropertyValueFactory<>("tuition"));
        CarsWeightColumn.setCellValueFactory(new PropertyValueFactory<>("longevity"));
    }

    private void loadCarsInGarage(int tankId) {
        CarsInGarageDAO carsInGarageDAO = new CarsInGarageDAO();
        List<Cars> carsInGarage = carsInGarageDAO.findAllCarsInGarage(tankId);
        if (!carsInGarage.isEmpty()) {
            List<String> carsModels = carsInGarage.stream().map(Cars::getModel).collect(Collectors.toList());
            DeleteCarsComboBox.setItems(FXCollections.observableArrayList(carsModels));
            CarsTableView.setItems(FXCollections.observableArrayList(carsInGarage));
        } else {
            DeleteCarsComboBox.getItems().clear();
            CarsTableView.getItems().clear();
        }
    }

    public void addCarsToGarage() {
        String selectedGarageLocation = GaragesComboBox.getValue();
        String carsModels = CarsComboBox.getValue();
        GarageDAO garageDAO = new GarageDAO();

        if (garageLocationToIdMap.containsKey(selectedGarageLocation)) {
            int garageId = garageLocationToIdMap.get(selectedGarageLocation);
            Garage garage = garageDAO.findById(garageId);
            CarsDAO carsDAO = new CarsDAO();
            Cars cars = carsDAO.findCarsByModel(carsModels);

            if (garage != null && cars != null) {
                CarsInGarageDAO carsInGarageDAO = CarsInGarageDAO.build();
                if (carsInGarageDAO.isCarsInGarage(garageId, cars.getId())) {;
                    AppController.ShowAlertsCarsAlreadyExistsInGarage();
                } else {
                    carsInGarageDAO.addCarsToGarage(garageId, cars.getId());
                    AppController.ShowAlertsSuccessfullyAddCarsToGarage();
                }
                loadCarsInGarage(garageId);
            }
        }
    }

    public void deleteCarsFromGarage() {
        String selectedGarageLocations = GaragesComboBox.getValue();
        String carsModels = DeleteCarsComboBox.getValue();
        GarageDAO garageDAO = new GarageDAO();

        if (garageLocationToIdMap.containsKey(selectedGarageLocations)) {
            int garageId = garageLocationToIdMap.get(selectedGarageLocations);
            Garage garage = garageDAO.findById(garageId);
            CarsDAO carsDAO = new CarsDAO();
            Cars cars = carsDAO.findCarsByModel(carsModels);

            if (garage != null && cars != null) {
                CarsInGarageDAO.build().removeCarsFromGarage(garageId, cars.getId());
                loadCarsInGarage(garageId);
            }
        }
    }

    public void changeSceneToGarageConfig() throws IOException {
        App.currentController.changeScene(Scenes.GARAGECONFIG, null);
    }
}
