package com.github.juan.view;

import com.github.juan.Model.Singleton.UserSession;
import com.github.juan.Model.dao.UserDAO;
import com.github.juan.Model.entity.User;
import com.github.juan.utils.PasswordHasher;
import com.github.juan.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Button button;

    @FXML
    Button buttonRegister;

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public User getValues(){
        User user = new User();
        String usernames = username.getText();
        String passwords = password.getText();
        if (!usernames.isEmpty() && !passwords.isEmpty()) {
            user.setUsername(usernames);
            user.setPassword(passwords);
            return user;
        } else {
            return null;
        }
    }

    public void Login() throws IOException {
        User user = getValues();
        UserDAO uDAO = new UserDAO();

        if (user == null) {
            AppController.ShowAlertsErrorLogin2();
        } else {
            User userFromDB = uDAO.findByUsername(user.getUsername());

            if (userFromDB != null) {
                String hashedInputPassword = PasswordHasher.hashPassword(user.getPassword());

                if (hashedInputPassword != null && hashedInputPassword.equals(userFromDB.getPassword())) {
                    UserSession.login(userFromDB);
                    changeSceneToMainPage();
                } else {
                    AppController.ShowAlertsErrorLoginPassword();
                }
            } else {
                AppController.ShowAlertsErrorLogin();
            }
        }
    }

    public void changeSceneToMainPage() throws IOException{
        App.currentController.changeScene(Scenes.MAIN,null);
    }

    public void changeSceneToRegister() throws IOException{
        App.currentController.changeScene(Scenes.REGISTER,null);
    }
}
