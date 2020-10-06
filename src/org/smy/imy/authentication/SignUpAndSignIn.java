package org.smy.imy.authentication;

import org.smy.imy.datasource.Database;


public class SignUpAndSignIn {

    // == static fields ==
    private static SignUpAndSignIn signUpAndSignIn = null;

    // == static final fields ==

    private final int SALT_ROUNDS = 10;

    // == constructors ==

    private SignUpAndSignIn() {}

    // == public static methods ==

    public static SignUpAndSignIn getInstance() {
        if (signUpAndSignIn == null)
            signUpAndSignIn = new SignUpAndSignIn();

        return signUpAndSignIn;
    }

    // == public methods ==

    public boolean signUpUser(String username,String password) {

        try {

        if (Database.getInstance().findUsername(username)) {
            System.out.println("user already in db");
            return false;
        }

        byte[] salt = BCrypt.gensalt(SALT_ROUNDS).getBytes();

        byte[] hash = BCrypt.hashpw(password, new String(salt)).getBytes();

        return Database.getInstance().addUser(username,hash);

        } catch (Exception e) {
            System.out.println("signUpUser() Exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public boolean signInUser(String username,String password) {

        try {

        if (!Database.getInstance().findUsername(username)) {
            System.out.println("username not found!");
                return false;
        }

        String hash = new String(Database.getInstance().retrieveHash(username));

        return BCrypt.checkpw(password,hash);

        } catch (Exception e) {
            System.out.println("signInUser() error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

}
