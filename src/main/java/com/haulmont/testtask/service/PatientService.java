package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.DAO;
import com.haulmont.testtask.entity.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PatientService implements DAO<Patient> {
    ConnectionManager connectionManager = new ConnectionManager();
    Connection connection;
    PreparedStatement preparedStatement = null;

    @Override
    public void add(Patient patient) {
        connection = connectionManager.getConnection();
        String sql = "INSERT INTO PATIENT(firstname,LASTNAME,SECONDNAME,PHONE)VALUES(?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getSecondName());
            preparedStatement.setString(3, patient.getLastName());
            preparedStatement.setString(4, patient.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null || connection != null) {
                try {
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Patient> getAll() {
        connection = connectionManager.getConnection();
        List<Patient> patients = new LinkedList<>();
        String sql = "SELECT * FROM PATIENT";

        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getLong("id"));
                patient.setFirstName(resultSet.getString("firstname"));
                patient.setSecondName(resultSet.getString("secondname"));
                patient.setLastName(resultSet.getString("lastname"));
                patient.setPhone(resultSet.getString("phone"));
                patient.setSelect(false);
                patients.add(patient);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (preparedStatement != null || connection != null) {
                try {
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return patients;
    }

    @Override
    public Patient getbyId(long id) {
        connection = connectionManager.getConnection();
        Patient patient = new Patient();
        String sql = "SELECT id,firstname,secondname,lastname,phone FROM patient WHERE ID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            patient.setId(resultSet.getLong("id"));
            patient.setFirstName(resultSet.getString("firstname"));
            patient.setSecondName(resultSet.getString("secondname"));
            patient.setLastName(resultSet.getString("lastname"));
            patient.setPhone(resultSet.getString("phone"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null || connection != null) {
                try {
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return patient;
    }

    @Override
    public void update(Patient patient){
        connection = connectionManager.getConnection();
        String sql = "UPDATE PATIENT SET firstname=?,secondName=?,lastName=?,specialization=? where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getSecondName());
            preparedStatement.setString(3, patient.getLastName());
            preparedStatement.setString(4, patient.getPhone());
            preparedStatement.setLong(5, patient.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null || connection != null) {
                try {
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public void remove(Patient patient){
        connection = connectionManager.getConnection();
        String sql = "DELETE FROM PATIENT Where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, patient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null || connection != null) {
                try {
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
