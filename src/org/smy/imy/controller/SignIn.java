package org.smy.imy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.smy.imy.authentication.SignUpAndSignIn;
import org.smy.imy.model.Item;
import org.smy.imy.util.SceneSelector;

public class SignIn {

    @FXML
    TextField username;

    @FXML
    PasswordField password;


    @FXML
    public void redirectToSignUp() throws Exception {
        SceneSelector.switchScreen("signUp");
    }

    @FXML
    public void signInUser() {

        if (!SignUpAndSignIn.getInstance().signInUser(username.getText(),password.getText())) {
            System.out.println("can't signIn....");
            return;
        }

        Item.signInUsername = username.getText();

        SceneSelector.switchScreen("inventory");
        Inventory.loadInventory();


    }


}
