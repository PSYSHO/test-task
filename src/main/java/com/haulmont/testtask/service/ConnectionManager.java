package com.haulmont.testtask.service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    static Connection connection;
    public static final String url = "jdbc:hsqldb:file:src/main/java/com/haulmont/testtask/DB/db";
    //"jdbc:hsqldb:mem:task";
    public static final String user = "SA";
    public static final String password = "";

    public static java.sql.Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            //System.out.println("Connection accept");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Conection EROOR");
        }
        return connection;
    }

    public static void createDB() {
        String createDB = null;
        createDB = readToString("src/main/resources/initDB.sql");
        /**use test bd**/
        /*DatabaseManagerSwing.main(new String[]
                {
                        "--url", "jdbc:hsqldb:mem:task", "--noexit"
                });*/
        try (Connection connection = ConnectionManager.getConnection()) {
            connection.createStatement().executeUpdate(createDB);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("DB create");
    }
    public static void fillingData(){
        String createData = readToString("src/main/resources/data.sql");
        try (Connection connection = ConnectionManager.getConnection()) {
            connection.createStatement().executeUpdate(createData);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Data in the filling");
    }

    public static String readToString(String fname) {
        File file = new File(fname);
        String string = null;
        try {
            string = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }
}
