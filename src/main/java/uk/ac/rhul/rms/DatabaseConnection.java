package uk.ac.rhul.rms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {


    private static Connection instance = null;

    private static Connection connection(){

        String protocol =
                "jdbc:sqlite:src/main/resources/SQLite/sqlite-tools-win32-x86-3370200/menudb.db";
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(protocol);
        } catch (SQLException e) { // catches SQL Exception
            String errorMsg = e.getMessage();
            if (errorMsg.contains("authentication failed")) {
                System.out.println("-----ERROR-----");
            } else {
                System.out.println("Connection failed!");
                e.printStackTrace();
            }
        }
        return connect;
    }

    public static Connection getInstance(){
        if (instance == null){
            instance = connection();
        }
        return instance;
    }
}
