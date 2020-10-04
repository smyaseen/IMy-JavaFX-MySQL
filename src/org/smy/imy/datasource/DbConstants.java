package org.smy.imy.datasource;

public final class DbConstants {

    // == constructors ==

    private DbConstants() {}

    // == database names ==

    public static final String DATABASE_IMY = "imy";
    public static final String DATABASE_Inventory = "inventory";

    // == tables ==

    protected static final String TABLE_USERS = "users";
    protected static final String TABLE_INVENTORY = "inventory";

    // == columns ==

    protected static final String COLUMN_USERS_USERNAME = "username";
    protected static final String COLUMN_USERS_HASH = "hash";

    protected static final int INDEX_USERS_USERNAME = 1;
    protected static final int INDEX_USERS_HASH = 2;

    protected static final String COLUMN_INVENTORY_USERNAME = "username";
    protected static final String COLUMN_INVENTORY_ITEM_NAME = "item_name";
    protected static final String COLUMN_INVENTORY_ITEM_QUANTITY = "item_quantity";
    protected static final String COLUMN_INVENTORY_ITEM_SINGLE_PRICE = "item_single_stock_price";
    protected static final String COLUMN_INVENTORY_ITEM_FULL_STOCK_PRICE = "item_full_stock_price";
    protected static final String COLUMN_INVENTORY_ITEM_SERIAL_NUMBER = "item_serial_number";


    protected static final int INDEX_INVENTORY_USERNAME = 1;
    protected static final int INDEX_INVENTORY_ITEM_NAME = 2;
    protected static final int INDEX_INVENTORY_ITEM_QUANTITY = 3;
    protected static final int INDEX_INVENTORY_ITEM_SINGLE_PRICE = 4;
    protected static final int INDEX_INVENTORY_ITEM_FULL_STOCK_PRICE = 5;
    protected static final int INDEX_INVENTORY_ITEM_SERIAL_NUMBER = 6;


    // == queries ==

    protected static final String QUERY_ADD_USER = "INSERT INTO " + DATABASE_IMY + "." + TABLE_USERS + " VALUES(?,?)";

    protected static final String QUERY_SELECT_A_USERNAME = "SELECT  " + COLUMN_USERS_USERNAME + " FROM "
            + DATABASE_IMY + "." + TABLE_USERS  + " WHERE " + COLUMN_USERS_USERNAME + " = ?";

    protected static final String QUERY_SELECT_HASH_OF_A_USER = "SELECT " + COLUMN_USERS_HASH + " FROM "
            + DATABASE_IMY + "."  + TABLE_USERS + " WHERE " + COLUMN_USERS_USERNAME + " = ?";

    protected static final String QUERY_ADD_ITEM = "INSERT INTO " + DATABASE_IMY + "." + TABLE_INVENTORY + " VALUES(?,?,?,?,?,?)";

    protected static final String QUERY_FIND_ITEM = "SELECT " + COLUMN_INVENTORY_ITEM_NAME + "," + COLUMN_INVENTORY_ITEM_SERIAL_NUMBER
            + " FROM " + DATABASE_IMY + "." + TABLE_INVENTORY + " WHERE " + COLUMN_INVENTORY_ITEM_NAME + " = ?" + " AND " +
            COLUMN_INVENTORY_ITEM_SERIAL_NUMBER + " = ?";

    protected static final String QUERY_LOAD_USER_INVENTORY = "SELECT * FROM " + DATABASE_IMY + "." + TABLE_INVENTORY
            + " WHERE " + COLUMN_INVENTORY_USERNAME + " = ?";

    protected static final String QUERY_DELETE_ITEM = "DELETE FROM " + DATABASE_IMY + "." + TABLE_INVENTORY + " WHERE "
            + COLUMN_USERS_USERNAME + " = ? AND " + COLUMN_INVENTORY_ITEM_NAME + " = ?";

    protected static final String QUERY_UPDATE_QUANTITY_PRICE = "UPDATE " + DATABASE_IMY + "." + TABLE_INVENTORY +
            " SET " + COLUMN_INVENTORY_ITEM_QUANTITY  + " = ? , " + COLUMN_INVENTORY_ITEM_SINGLE_PRICE + " = ? WHERE " +
            COLUMN_INVENTORY_USERNAME + " = ? AND " + COLUMN_INVENTORY_ITEM_NAME + " = ?";

}
