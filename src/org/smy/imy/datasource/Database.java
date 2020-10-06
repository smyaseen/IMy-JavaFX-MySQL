package org.smy.imy.datasource;

import org.smy.imy.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    // == private static fields ==

    private static Database database = null;

    // == private final fields ==

    private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/imy";

    // == private fields ==

    private Connection connection;

    private PreparedStatement addUserPS;
    private PreparedStatement selectAUsernamePS;
    private PreparedStatement retrieveHashPS;
    private PreparedStatement selectItemNameNSerialNumberPS;
    private PreparedStatement addItemPS;
    private PreparedStatement loadUserInventoryPS;
    private PreparedStatement deleteItemPS;
    private PreparedStatement updateQuantityPricePS;

    private String username;

    // == constructors ==


    private Database() {
    }

    // == public static methods ==

    public static Database getInstance() {
        if (database == null)
            database = new Database();

        return database;
    }

    // == public static fields ==

    public boolean openDb() {

        try {

            connection = DriverManager.getConnection(CONNECTION_STRING, "root", "admin");

            selectAUsernamePS = connection.prepareStatement(DbConstants.QUERY_SELECT_A_USERNAME);

            addUserPS = connection.prepareStatement(DbConstants.QUERY_ADD_USER);

            retrieveHashPS = connection.prepareStatement(DbConstants.QUERY_SELECT_HASH_OF_A_USER);

            selectItemNameNSerialNumberPS = connection.prepareStatement(DbConstants.QUERY_FIND_ITEM);

            addItemPS = connection.prepareStatement(DbConstants.QUERY_ADD_ITEM);

            loadUserInventoryPS = connection.prepareStatement(DbConstants.QUERY_LOAD_USER_INVENTORY);

            deleteItemPS = connection.prepareStatement(DbConstants.QUERY_DELETE_ITEM);

            updateQuantityPricePS = connection.prepareStatement(DbConstants.QUERY_UPDATE_QUANTITY_PRICE);

            return true;

        } catch (Exception e) {
            System.out.println("openDb() error: " + e.getMessage());
//            e.printStackTrace();
            return false;
        }

    }

    public void closeDb() {
        try {

            if (selectAUsernamePS != null)
                selectAUsernamePS.close();

            if (retrieveHashPS != null)
                retrieveHashPS.close();

            if (addUserPS != null)
                addUserPS.close();


            if (selectItemNameNSerialNumberPS != null)
                selectItemNameNSerialNumberPS.close();


            if (addItemPS != null)
                addItemPS.close();


            if (loadUserInventoryPS != null)
                loadUserInventoryPS.close();

            if (deleteItemPS != null)
                deleteItemPS.close();

            if (updateQuantityPricePS != null)
                updateQuantityPricePS.close();

            connection.close();
        } catch (Exception e) {
            System.out.println("closeDb() error:" + e.getMessage());
//            e.printStackTrace();
        }
    }

    public boolean findUsername(String username) throws SQLException {
        selectAUsernamePS.setString(1, username);
        ResultSet resultSet = selectAUsernamePS.executeQuery();

        if (resultSet.next())
            return true;

        return false;
    }

    public boolean addUser(String username, byte[] hash) {

        try {

            addUserPS.setString(1, username);
            addUserPS.setBytes(2, hash);

            int affectedRows = addUserPS.executeUpdate();

            if (affectedRows != 1)
                throw new SQLException("Couldn't add user");

            return true;

        } catch (Exception e) {
            System.out.println("addUser() exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public byte[] retrieveHash(String username) throws SQLException {

        retrieveHashPS.setString(1, username);

        ResultSet resultSet = retrieveHashPS.executeQuery();

        resultSet.next();

        this.username = username;

        return resultSet.getBytes(1);
    }

    public boolean findItem(String itemName, String serialNumber) {

        try {

            selectItemNameNSerialNumberPS.setString(1, itemName);
            selectItemNameNSerialNumberPS.setString(2, serialNumber);

            ResultSet resultSet = selectItemNameNSerialNumberPS.executeQuery();

            if (resultSet.next())
                throw new Exception("item name or serial already found!");

            return true;

        } catch (Exception e) {
            System.out.println("findItem() exception: " + e.getMessage());
            return false;
        }

    }

    public boolean addItem(Item itemToAdd) {

        if (!findItem(itemToAdd.getItemName(), itemToAdd.getItemSerialNumber()))
            return false;


        try {

            addItemPS.setString(1, itemToAdd.getUsername());
            addItemPS.setString(2, itemToAdd.getItemName());
            addItemPS.setInt(3, itemToAdd.getItemQuantity());
            addItemPS.setInt(4, itemToAdd.getItemSingleStockPrice());
            addItemPS.setInt(5, itemToAdd.getItemFullStockPrice());
            addItemPS.setString(6, itemToAdd.getItemSerialNumber());

            int affectedRows = addItemPS.executeUpdate();

            if (affectedRows != 1)
                throw new Exception("Failed to add item");

            return true;

        } catch (Exception e) {
            System.out.println("addItem exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }


    }


    public List<Item> loadUserInventory() {

        try {

            loadUserInventoryPS.setString(1,username);
            ResultSet resultSet = loadUserInventoryPS.executeQuery();

            List<Item> items = new ArrayList<>();

            while (resultSet.next()) {

                String itemName = resultSet.getString(DbConstants.INDEX_INVENTORY_ITEM_NAME);
                int itemQuantity = resultSet.getInt(DbConstants.INDEX_INVENTORY_ITEM_QUANTITY);
                int itemSingleStockPrice = resultSet.getInt(DbConstants.INDEX_INVENTORY_ITEM_SINGLE_PRICE);
                String itemSerialNumber = resultSet.getString(DbConstants.INDEX_INVENTORY_ITEM_SERIAL_NUMBER);

                Item item = new Item(itemName,itemQuantity,itemSingleStockPrice,itemSerialNumber);

                items.add(item);
            }

            return items;

        } catch (Exception e) {
            System.out.printf("loadUserInventory error : " + e.getMessage());
            return null;
        }

    }

    public boolean deleteItem(Item itemToDelete) {

        try {
            if (!findUsername(username))
                    throw new Exception("Username not found!");

            if (findItem(itemToDelete.getItemName(),itemToDelete.getItemSerialNumber()))
                    throw new Exception("Item not found!");

            deleteItemPS.setString(1,username);
            deleteItemPS.setString(2,itemToDelete.getItemName());

            int rowsAffected = deleteItemPS.executeUpdate();

            if (rowsAffected != 1)
                throw new Exception("delete error!");

            return true;

        } catch (Exception e) {
            System.out.println("deleteItem exception: " + e.getMessage());
            return false;
        }

    }

    public boolean updateItem(Item itemToUpdate) {

        try {
            if (!findUsername(username))
                throw new Exception("Username not found!");

            if (findItem(itemToUpdate.getItemName(), itemToUpdate.getItemSerialNumber()))
                throw new Exception("Item not found!");

            updateQuantityPricePS.setInt(1,itemToUpdate.getItemQuantity());
            updateQuantityPricePS.setInt(2,itemToUpdate.getItemSingleStockPrice());
            updateQuantityPricePS.setString(3,itemToUpdate.getUsername());
            updateQuantityPricePS.setString(4,itemToUpdate.getItemName());

            System.out.println(updateQuantityPricePS.executeUpdate());

            return true;


        } catch (Exception e) {
            System.out.println("updateItem exception: " + e.getMessage());
            return false;
        }


        }


}
