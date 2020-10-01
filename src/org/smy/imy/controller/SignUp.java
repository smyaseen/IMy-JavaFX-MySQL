package org.smy.imy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.smy.imy.authentication.SignUpAndSignIn;
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

        System.out.println("clicked");

        if (SignUpAndSignIn.getInstance().signUpUser(username.getText(),password.getText()))
            System.out.println("user added");

        else
            System.out.println("user not added");


    }


}
