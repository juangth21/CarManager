package com.github.juan.view;

import com.github.juan.Model.dao.UserDAO;
import com.github.juan.Model.entity.User;
import com.github.juan.App;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserConfigController extends Controller implements Initializable {

    @FXML
    Button crear;
    @FXML
    ImageView volver;
    @FXML
    TableView<User> table;
    @FXML
    TableColumn<User, String> username;
    @FXML
    TableColumn<User, String> name;
    @FXML
    TableColumn<User, String> email;

    private ObservableList<User> users;


    @Override
    public void onOpen(Object input) throws IOException {
        UserDAO uDAO = new UserDAO();
        List<User> users = uDAO.findAll();
        this.users= FXCollections.observableArrayList(users);
        table.setItems(this.users);

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDAO uDAO = new UserDAO();
        table.setEditable(true);
        username.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getUsername()));
        name.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getName()));
        email.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getEmail()));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }
            User user = event.getRowValue();
            user.setName(event.getNewValue());
            uDAO.update(user);
        });
        email.setOnEditCommit(event2 -> {
            if (event2.getNewValue().equals(event2.getOldValue())) {
                return;
            }
            User user = event2.getRowValue();
            user.setEmail(event2.getNewValue());
            uDAO.update(user);
        });

    }
    public void changeSceneToRegister() throws IOException{
        App.currentController.changeScene(Scenes.REGISTER,null);
    }
    public void changeSceneToMainPage() throws IOException{
        App.currentController.changeScene(Scenes.MAIN,null);
    }
    public void changeSceneToDeleteUser() throws IOException{
        App.currentController.changeScene(Scenes.DELETEUSER,null);
    }





}
