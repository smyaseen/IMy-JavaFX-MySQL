package org.smy.imy.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.smy.imy.datasource.Database;

public class ItemData {

    // == private static fields ==

    private static ItemData itemData = null;

    // == private fields ==

    // == constructors ==

    private ItemData() {}

    // == public static methods ==

    public static ItemData getInstance() {
        if (itemData == null)
            itemData = new ItemData();

        return itemData;
    }

    // == private final fields ==

    private final ObservableList<Item> items = FXCollections.observableArrayList();

    // == public methods ==

    public int addItem(Item itemToAdd) {

        if (itemToAdd == null)
            return -1;

        if (!itemToAdd.getItemName().matches("^[A-Za-z0-9\\s]+"))
            return 1;

        if (itemToAdd.getItemName().isBlank() || itemToAdd.getItemName().isEmpty()
            || itemToAdd.getItemName() == null)
            return 2;

        if (itemToAdd.getItemQuantity() <= 0)
            return 3;

        if (itemToAdd.getItemSingleStockPrice() <= 0)
            return 4;

        if (!itemToAdd.getItemSerialNumber().matches("^[A-Za-z0-9\\s]+"))
            return 5;

        if (itemToAdd.getItemSerialNumber().isBlank() || itemToAdd.getItemSerialNumber().isEmpty()
                || itemToAdd.getItemSerialNumber() == null)
            return 6;

        if(Database.getInstance().addItem(itemToAdd)) {
            return 0;
        }


        return -2;
    }

    public ObservableList<Item> getItems() {
       items.addAll(Database.getInstance().loadUserInventory());
        return items;
    }

}
