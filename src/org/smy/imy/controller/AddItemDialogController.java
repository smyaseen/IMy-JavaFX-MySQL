package org.smy.imy.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.smy.imy.model.Item;
import org.smy.imy.model.ItemData;

public class AddItemDialogController {

    @FXML
    private TextField itemName;

    @FXML
    private Spinner itemQuantity;

    @FXML
    private Spinner itemSingleStockPrice;

    @FXML
    private TextField itemSerialNumber;

    @FXML
    private Window window;

    @FXML
    private Button addButton;


    private Item item = null;

    public Item processResult() {

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                item = addItem();

        if (item != null)
           ItemData.getInstance().getItems().add(item);

            }
        });

        return item;
    }


    public Item addItem() {

        if (itemName.getText().isEmpty() || itemSerialNumber.getText().isEmpty()) {
            showAlert("Empty field(s)!");
            return null;
        }

        Item item = new Item(itemName.getText().trim(), (int) itemQuantity.getValue(),
                (int) itemSingleStockPrice.getValue(), itemSerialNumber.getText().trim());

        int result = ItemData.getInstance().addItem(item);

        String text = "";

        if (result != 0) {

            switch (result) {
                case -1:
                    text = "error item name null!";
                    break;
                case 1:
                    text = "item name must be alphabets and numbers only!";
                    break;
                case 2:
                    text = "item name must not be empty!";
                    break;
                case 3:
                    text = "item quantity must be greater then 0!";
                    break;
                case 4:
                    text = "item price must be greater then 0!";
                    break;
                case 5:
                    text = "item serial number must be number or alphabets only!";
                    break;
                case 6:
                    text = "error item serial number null!";
                    break;
                default:
                    text = "error adding item!";
                    break;
            }

            showAlert(text);
            return null;
        }


        return item;
    }

    private void showAlert(String text) {

        Alert inputAlert = new Alert(Alert.AlertType.ERROR);
        inputAlert.initOwner(addButton.getScene().getWindow());
        inputAlert.setHeaderText("input error");
        inputAlert.setContentText(text);

        inputAlert.showAndWait();


    }
}