package org.smy.imy.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.smy.imy.model.Item;
import org.smy.imy.model.ItemData;

import java.io.IOException;

public class Inventory {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<Item> tableView;

    @FXML
    private TableColumn<Item,String> itemName;
    @FXML
    private TableColumn<Item,Integer> itemQuantity;
    @FXML
    private TableColumn<Item,Integer> itemSingleStockPrice;
    @FXML
    private TableColumn<Item,Integer> itemFullStockPrice;
    @FXML
    private TableColumn<Item,String> itemSerialNumber;

    private static ObservableList<Item> items = FXCollections.observableArrayList();

    public void initialize() {

        System.out.printf("llloooppp");


        itemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<>("ItemQuantity"));
        itemSingleStockPrice.setCellValueFactory(new PropertyValueFactory<>("ItemSingleStockPrice"));
        itemFullStockPrice.setCellValueFactory(new PropertyValueFactory<>("ItemFullStockPrice"));
        itemSerialNumber.setCellValueFactory(new PropertyValueFactory<>("ItemSerialNumber"));
//
//        Item item = new Item("asda",2,3,"123");
//        items.add(item);

        tableView.setItems(items);

    }

    public static void loadInventory() {
        items.addAll(ItemData.getInstance().getItems());
    }

    @FXML
    public void openAddItemDialog() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/org/smy/imy/view/addItemDialog.fxml"));

        dialog.setTitle("Add New Item");
        dialog.setHeaderText("This Adds new Item");


        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }


        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

//        Optional<ButtonType> result = dialog.showAndWait();
//
//        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
//            dialog.close();
//        return;
//        }

        dialog.show();

        AddItemDialogController addItemDialogController = fxmlLoader.getController();

        Item newItem = addItemDialogController.processResult();

    }

    public static void addItemToTable(Item item) {
        items.add(item);
    }
}