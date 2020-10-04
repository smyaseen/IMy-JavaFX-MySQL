package org.smy.imy.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.smy.imy.datasource.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ItemData {

    // == private static fields ==

    private static ItemData itemData = null;

    // == private fields ==

    Predicate<Item> filteredItems;

    private FilteredList<Item> filteredList;

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

    public void loadItems() {
       items.addAll(Database.getInstance().loadUserInventory());
    }

    public ObservableList<Item> getItems() {
       return items;
    }

    public boolean deleteItem(Item itemToDelete) {

        if (!Database.getInstance().deleteItem(itemToDelete))
            return false;

        return items.remove(itemToDelete);

    }


    public boolean updateItem(Item itemToUpdate) {

        if (!Database.getInstance().updateItem(itemToUpdate))
            return false;

        else {

            int i = 0;

            for (; i < items.size() ; i++) {

                if (items.get(i).getItemName().equals(itemToUpdate.getItemName())) {
                    items.get(i).setItemQuantity(itemToUpdate.getItemQuantity());
                    items.get(i).setItemQuantity(itemToUpdate.getItemSingleStockPrice());
                    break;
                }

            }

           return i != items.size();
        }

    }

    public List<Item> getFilteredItems(String searchItem) {

        filteredItems = new Predicate<Item>() {
            @Override
            public boolean test(Item item) {
                return item.getItemName().contains(searchItem);
            }
        };

        filteredList = new FilteredList<>(items,filteredItems);

        return new ArrayList<>(filteredList);

    }

}
