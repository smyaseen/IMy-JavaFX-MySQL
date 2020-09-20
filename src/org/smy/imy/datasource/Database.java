package org.smy.imy.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Database {

    private static Connection connection;

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/imy";

    public static final String TABLE_USERS = "users";

    public static final String COLUMN_USERS_USERNAME = "username";
    public static final String COLUMN_USERS_HASH = "hash";
    public static final String COLUMN_USERS_PASSWORD = "password";

    public static final String QUERY_ADD_USER = "INSERT INTO " + TABLE_USERS + " VALUES(?,?,?)";

    private static PreparedStatement addUserPS;

    private Database() {}


    public static boolean openDb() {

        try {

            connection = DriverManager.getConnection(CONNECTION_STRING,"root","admin");

            addUserPS = connection.prepareStatement(QUERY_ADD_USER);

            return true;
        } catch (Exception e) {
            System.out.println("openDb() error: " + e.getMessage());
//            e.printStackTrace();
            return false;
        }

    }

    public static void closeDb() {
        try {

            if (addUserPS != null)
                    addUserPS.close();


            connection.close();
        } catch (Exception e) {
            System.out.println("closeDb() error:" + e.getMessage());
//            e.printStackTrace();
        }
    }

    public static void addUser(String username,String password) {




    }


}
