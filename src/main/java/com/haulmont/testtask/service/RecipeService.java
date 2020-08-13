package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.DAO;
import com.haulmont.testtask.entity.Priority;
import com.haulmont.testtask.entity.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RecipeService implements DAO<Recipe> {
    DoctorService doctorService;
    PatientService patientService;
    Util util = new Util();
    Connection connection = util.getConnection();
    PreparedStatement preparedStatement = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

    @Override
    public void add(Recipe recipe) throws SQLException {
        String sql = "INSERT INTO RECIPE(ID,DESCRIPTION,PATIENT,DOCTOR,CREATEDATA,VALIDATE,PRIORITY)VALUES(?,?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, recipe.getId());
            preparedStatement.setString(2, recipe.getDescription());
            preparedStatement.setLong(3, recipe.getPatient().getId());
            preparedStatement.setLong(4, recipe.getDoctor().getId());
            preparedStatement.setDate(5, java.sql.Date.valueOf(dateFormat.format(new Date())));
            preparedStatement.setString(6, recipe.getValidate());
            preparedStatement.setString(7, String.valueOf(recipe.getPriority()));
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


    //todo Реализацию данного метода можно сделать без использования сервисов, заменив их написание большого SQL запроса.
    @Override
    public List<Recipe> getAll() throws SQLException {
        List<Recipe> recipes = new LinkedList<>();
        String sql = "SELECT * FROM RECIPE";
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery(sql);
        while (resultSet.next()) {
            Recipe recipe = new Recipe();
            recipe.setId(resultSet.getLong("id"));
            fillingInnTheObject(recipe, resultSet);
            recipes.add(recipe);
        }
        return recipes;
    }

    @Override
    public Recipe getbyId(long id) throws SQLException {
        Recipe recipe =new Recipe();
        String sql = "SELECT id,description,patient,doctor,createdata,validate,priority FROM Recipe WHERE ID=?";
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        recipe.setId(resultSet.getLong("id"));
        fillingInnTheObject(recipe, resultSet);
        return recipe;
    }

    private void fillingInnTheObject(Recipe recipe, ResultSet resultSet) throws SQLException {
        recipe.setCreateDate(resultSet.getDate("createdata"));
        recipe.setDescription(resultSet.getString("description"));
        recipe.setDoctor(doctorService.getbyId(resultSet.getLong("doctor")));
        recipe.setPatient(patientService.getbyId(resultSet.getLong("patient")));
        recipe.setPriority(Priority.valueOf(resultSet.getString("priority")));
        recipe.setValidate(resultSet.getString("validate"));
    }

    @Override
    public void update(Recipe recipe) throws SQLException {
        String sql = "UPDATE RECIPE SET ID=?,DESCRIPTION=?,PATIENT=?,DOCTOR=?,CREATEDATA=?,VALIDATE=?,PRIORITY=? where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, recipe.getId());
            preparedStatement.setString(2,recipe.getDescription());
            preparedStatement.setLong(3,recipe.getPatient().getId());
            preparedStatement.setLong(4,recipe.getDoctor().getId());
            preparedStatement.setDate(5, (java.sql.Date) recipe.getCreateDate());
            preparedStatement.setString(6,recipe.getValidate());
            preparedStatement.setString(7, String.valueOf(recipe.getPriority()));
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
    public void remove(Recipe recipe) throws SQLException {
        String sql = "DELETE FROM Recipe where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, recipe.getId());
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
