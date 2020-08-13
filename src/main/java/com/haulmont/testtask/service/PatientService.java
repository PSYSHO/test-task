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
    Util util = new Util();
    Connection connection ;
    PreparedStatement preparedStatement = null;

    @Override
    public void add(Patient patient) throws SQLException {
        Connection connection = util.getConnection();
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
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        Connection connection = util.getConnection();
        List<Patient> patients = new LinkedList<>();
        String sql = "SELECT * FROM PATIENT";
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
        return patients;
    }

    @Override
    public Patient getbyId(long id) throws SQLException {
        Connection connection = util.getConnection();
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
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return patient;
    }

    @Override
    public void update(Patient patient) throws SQLException {
        Connection connection = util.getConnection();
        String sql = "UPDATE PATIENT SET id=?,firstname=?,secondName=?,lastName=?,specialization=? where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, patient.getId());
            preparedStatement.setString(2, patient.getFirstName());
            preparedStatement.setString(3, patient.getSecondName());
            preparedStatement.setString(4, patient.getLastName());
            preparedStatement.setString(5, patient.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void remove(Patient patient) throws SQLException {
        Connection connection = util.getConnection();
        String sql = "DELETE FROM PATIENT Where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, patient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
