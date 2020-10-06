package org.smy.imy.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {

    // == fields ==

    public static String signInUsername;

    private final SimpleStringProperty username;
    private final SimpleStringProperty itemName;
    private SimpleIntegerProperty itemQuantity;
    private SimpleIntegerProperty itemSingleStockPrice;
    private SimpleIntegerProperty itemFullStockPrice;
    private final SimpleStringProperty itemSerialNumber;

    // == constructors ==

    public Item(String itemName, int itemQuantity, int itemSingleStockPrice, String itemSerialNumber) {
        this.username = new SimpleStringProperty(signInUsername);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemQuantity = new SimpleIntegerProperty(itemQuantity);
        this.itemSingleStockPrice = new SimpleIntegerProperty(itemSingleStockPrice);
        this.itemFullStockPrice = new SimpleIntegerProperty(itemSingleStockPrice * itemQuantity);
        this.itemSerialNumber = new SimpleStringProperty(itemSerialNumber);
    }

    // == getter setters ==

    public String getItemName() {
        return itemName.get();
    }

    public int getItemQuantity() {
        return itemQuantity.get();
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity.set(itemQuantity);
        setFullStockPrice();
    }

    public int getItemSingleStockPrice() {
        return itemSingleStockPrice.get();
    }

    public String getUsername() {
        return username.get();
    }

    public void setItemSingleStockPrice(int itemSingleStockPrice) {
        this.itemSingleStockPrice.set(itemSingleStockPrice);
        setFullStockPrice();
    }

    public int getItemFullStockPrice() {
        return itemFullStockPrice.get();
    }

    private void setFullStockPrice() {
        this.itemFullStockPrice.set(itemSingleStockPrice.get()* itemQuantity.get());
    }

    public String getItemSerialNumber() {
        return itemSerialNumber.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    public SimpleIntegerProperty itemQuantityProperty() {
        return itemQuantity;
    }

    public SimpleIntegerProperty itemSingleStockPriceProperty() {
        return itemSingleStockPrice;
    }

    public SimpleIntegerProperty itemFullStockPriceProperty() {
        return itemFullStockPrice;
    }

    public SimpleStringProperty itemSerialNumberProperty() {
        return itemSerialNumber;
    }
}
