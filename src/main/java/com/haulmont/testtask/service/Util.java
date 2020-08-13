package com.haulmont.testtask.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static final String url = "jdbc:hsqldb:mem:task";
    public static final String user ="SA";
    public static final String password = "";
    public java.sql.Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,user,password);
            System.out.println("Connection accept");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Conection EROOR");
        }
        return connection;
    }
}
