package org.smy.imy.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.smy.imy.authentication.SignUpAndSignIn;
import org.smy.imy.model.Item;
import org.smy.imy.util.SceneSelector;

public class SignInController {

    @FXML
    TextField username;

    @FXML
    PasswordField password;
    @FXML
    ImageView signInImg;
    @FXML
    ImageView signUpImg;


    public void initialize() {

    signInImg.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            signInImg.setFitWidth(signInImg.getFitWidth()/2);
            signInImg.setFitHeight(signInImg.getFitHeight()/2);
        }
    });

        signInImg.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                signInImg.setFitWidth(signInImg.getFitWidth()*2);
                signInImg.setFitHeight(signInImg.getFitHeight()*2);
            }
        });

    }

    @FXML
    public void signInEnterKeyHandler(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
        signInUser();
    }

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
        InventoryController.loadInventory();

    }


}
