package org.smy.imy.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.smy.imy.model.Item;
import org.smy.imy.model.ItemData;
import org.smy.imy.util.SceneSelector;

import java.io.IOException;
import java.util.Optional;

public class InventoryController {

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
    @FXML
    private Text itemToDisplay;
    @FXML
    private Spinner<Integer> quantitySpinner;
    @FXML
    private Spinner<Integer> priceSpinner;
    @FXML
    private TextField searchField;

    private ContextMenu listContextMenu;

    public void initialize() {


        itemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<>("ItemQuantity"));
        itemSingleStockPrice.setCellValueFactory(new PropertyValueFactory<>("ItemSingleStockPrice"));
        itemFullStockPrice.setCellValueFactory(new PropertyValueFactory<>("ItemFullStockPrice"));
        itemSerialNumber.setCellValueFactory(new PropertyValueFactory<>("ItemSerialNumber"));

        listContextMenu = new ContextMenu();

        MenuItem deleteContext = new MenuItem("delete");
        deleteContext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteItem();
            }
        });

        listContextMenu.getItems().add(deleteContext);

            tableView.setItems(ItemData.getInstance().getItems());
            tableView.setContextMenu(listContextMenu);
    }

    public static void loadInventory() {
        ItemData.getInstance().loadItems();
    }

    @FXML
    public void openAddItemDialog() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/org/smy/imy/view/addItemDialogView.fxml"));

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

        dialog.show();

        AddItemDialogController addItemDialogController = fxmlLoader.getController();

        Item newItem = addItemDialogController.processResult();

    }

    @FXML
    public void signOut() {
        SceneSelector.switchScreen("signIn");
        // clear inventory
        ItemData.getInstance().getItems().removeAll(ItemData.getInstance().getItems());
    }

    @FXML
    public void handleDeleteKey(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.DELETE)
            deleteItem();

    }

    private void deleteItem() {

       Item itemToDelete = tableView.getSelectionModel().getSelectedItem();

        if (!(showAlert("are you sure to delete: " + itemToDelete.getItemName(),
                "Delete Item Alert!",
                Alert.AlertType.CONFIRMATION) == ButtonType.OK))

            return;

       if (ItemData.getInstance().deleteItem(itemToDelete))
           showAlert("Item: " + itemToDelete.getItemName() + " deleted!","Item Deleted"
           , Alert.AlertType.INFORMATION);

       else
           showAlert("Item: " + itemToDelete.getItemName() + " could not be deleted!","Item Delete Error!"
                   , Alert.AlertType.ERROR);

    }

    private ButtonType showAlert(String content, String header, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.initOwner(borderPane.getScene().getWindow());
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(null);

    }

    @FXML
    public void displayItem() {

        try {

        Item item = tableView.getSelectionModel().getSelectedItem();

        itemToDisplay.setText(item.getItemName());

        quantitySpinner.setValueFactory(new
                SpinnerValueFactory.IntegerSpinnerValueFactory(1,999,item.getItemQuantity()));

        priceSpinner.setValueFactory(new
                SpinnerValueFactory.IntegerSpinnerValueFactory(1,999,item.getItemSingleStockPrice()));

        } catch (Exception e) {
            // do nothing
        }

    }

    @FXML
    public void updateItem() {

            Item itemToUpdate = tableView.getSelectionModel().getSelectedItem();

        if (!(showAlert("are you sure to update: " + itemToUpdate.getItemName(),
                "Update Item Alert!",
                Alert.AlertType.CONFIRMATION) == ButtonType.OK))

            return;

        itemToUpdate.setItemQuantity(quantitySpinner.getValue());
        itemToUpdate.setItemSingleStockPrice(priceSpinner.getValue());

        if (!ItemData.getInstance().updateItem(itemToUpdate))
            showAlert("Item: " + itemToUpdate.getItemName() + " could not be updated"
            ,"Update Error!", Alert.AlertType.ERROR);

        else {
            showAlert("Item: " + itemToUpdate.getItemName() + " updated!","Item Updated!"
                    , Alert.AlertType.INFORMATION);
            tableView.refresh();
        }

    }

    @FXML
    public void showAllItems() {
        tableView.setItems(ItemData.getInstance().getItems());
    }

    @FXML
    public void showFilteredItems(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER)

        tableView.setItems(FXCollections.observableArrayList(ItemData.getInstance()
                .getFilteredItems(searchField.getText())));

    }

}