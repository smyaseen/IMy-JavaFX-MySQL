package org.smy.imy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.smy.imy.datasource.Database;
import org.smy.imy.util.SceneSelector;

public class SignUp {

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    public void redirectToSignIn() {
        SceneSelector.switchScreen("signIn");
    }

    @FXML
    public void signUpUser() {
        Database.addUser(username.getText(),password.getText());
    }


}
