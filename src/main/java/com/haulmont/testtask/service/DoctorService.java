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
    Util util = new Util();
    Connection connection = util.getConnection();
    PreparedStatement preparedStatement = null;

    @Override
    public void add(Doctor doctor) throws SQLException {
        connection=  util.getConnection();
        String sql = "Insert INTO DOCTOR(firstname,secondname,lastname,specialization)VALUES(?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getSecondName());
            preparedStatement.setString(3, doctor.getLastName());
            preparedStatement.setString(4, doctor.getSpecialization());
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
    public List<Doctor> getAll() throws SQLException {
        List<Doctor> doctors = new LinkedList<>();
        connection=  util.getConnection();
        String sql = "SELECT * FROM DOCTOR";
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Doctor doctor = new Doctor();
            doctor.setId(resultSet.getLong("id"));
            doctor.setFirstName(resultSet.getString("firstname"));
            doctor.setSecondName(resultSet.getString("secondname"));
            doctor.setLastName(resultSet.getString("lastName"));
            doctor.setSpecialization(resultSet.getString("specialization"));
            doctor.setSelect(false);
            doctors.add(doctor);
        }
        return doctors;
    }

    @Override
    public Doctor getbyId(long id) throws SQLException {
        Doctor doctor = new Doctor();
        String sql = "SELECT id,firstname,secondname,lastname,specialization FROM Doctor WHERE ID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            doctor.setId(resultSet.getLong("id"));
            doctor.setFirstName(resultSet.getString("name"));
            doctor.setSecondName(resultSet.getString("secondName"));
            doctor.setLastName(resultSet.getString("lastName"));
            doctor.setSpecialization(resultSet.getString("specialization"));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return doctor;
    }

    @Override
    public void update(Doctor doctor) throws SQLException {
        String sql= "UPDATE DOCTOR SET id=?,firstname=?,secondname=?,lastname=?,specialization=?Where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, doctor.getId());
            preparedStatement.setString(2, doctor.getFirstName());
            preparedStatement.setString(3, doctor.getSecondName());
            preparedStatement.setString(4, doctor.getLastName());
            preparedStatement.setString(5, doctor.getSpecialization());
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
    public void remove(Doctor doctor) throws SQLException {
        connection=  util.getConnection();
        String sql = "DELETE FROM DOCTOR Where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, doctor.getId());
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
