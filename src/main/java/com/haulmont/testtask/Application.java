package com.haulmont.testtask;

import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.service.DoctorService;
import org.apache.commons.io.FileUtils;
import org.hsqldb.util.DatabaseManagerSwing;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    static Connection connection;
    static String connectionString = "jdbc:hsqldb:mem:task";


    public static void main(String[] args) throws Exception {
        String createDB = readToString("src/main/resources/initDB.sql");
        DoctorService doctorService= new DoctorService();

        System.out.println("Launching manager");
        DatabaseManagerSwing.main(new String[] {
                "--url", "jdbc:hsqldb:mem:task", "--noexit"});
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        }catch (ClassNotFoundException e){
            throw e;
        }
        try {
         connection = DriverManager.getConnection(connectionString,"SA","");
         connection.createStatement().executeUpdate(createDB);
        }catch (SQLException e){
            throw e;
        }finally {
            connection.close();
        }
        Doctor doctor  = new Doctor();doctor.setId(Long.valueOf(1));doctor.setFirstName("kevin");doctor.setLastName("jonns");doctor.setSecondName("Franc");doctor.setSpecialization("Orto");

        doctorService.add(doctor);

    }
    public static String readToString(String fname) throws Exception {
        File file = new File(fname);
        String string = FileUtils.readFileToString(file, "utf-8");
        return string;
    }
}
