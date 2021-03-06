package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.DAO;
import com.haulmont.testtask.entity.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DoctorService implements DAO<Doctor> {


    @Override
    public void add(Doctor doctor) {
        String sql = "Insert INTO DOCTOR(firstname,secondname,lastname,specialization)VALUES(?,?,?,?)";
        try(Connection connection = ConnectionManager.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getSecondName());
            preparedStatement.setString(3, doctor.getLastName());
            preparedStatement.setString(4, doctor.getSpecialization());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> doctors = new LinkedList<>();
        String sql = "SELECT * FROM DOCTOR";
        try (Connection connection = ConnectionManager.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(resultSet.getLong("id"));
                doctor.setFirstName(resultSet.getString("firstname"));
                doctor.setSecondName(resultSet.getString("secondname"));
                doctor.setLastName(resultSet.getString("lastName"));
                doctor.setSpecialization(resultSet.getString("specialization"));
                doctor.setSelect(false);
                doctors.add(doctor);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return doctors;
    }

    @Override
    public Doctor getbyId(long id) {
        Doctor doctor = new Doctor();
        String sql = "SELECT id,firstname,secondname,lastname,specialization FROM Doctor WHERE ID=?";
        try(Connection connection = ConnectionManager.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            doctor.setId(resultSet.getLong("id"));
            doctor.setFirstName(resultSet.getString("name"));
            doctor.setSecondName(resultSet.getString("secondName"));
            doctor.setLastName(resultSet.getString("lastName"));
            doctor.setSpecialization(resultSet.getString("specialization"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctor;
    }

    @Override
    public void update(Doctor doctor) {
        String sql = "UPDATE DOCTOR SET firstname=?,secondname=?,lastname=?,specialization=?Where id=?";
        try(Connection connection = ConnectionManager.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getSecondName());
            preparedStatement.setString(3, doctor.getLastName());
            preparedStatement.setString(4, doctor.getSpecialization());
            preparedStatement.setLong(5, doctor.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(Doctor doctor) {
        boolean check = false;
        String sql = "DELETE FROM DOCTOR Where id=?";
        try(Connection connection = ConnectionManager.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, doctor.getId());
            preparedStatement.executeUpdate();
            check = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }
}
