package org.smy.imy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.smy.imy.util.SceneSelector;

public class SignIn {

    @FXML
    private Button signUpButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    public void redirectToSignUp() throws Exception {
        SceneSelector.switchScreen("signUp");
    }


}
