package com.haulmont.testtask.service;

import com.haulmont.testtask.dao.DAO;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.entity.Priority;
import com.haulmont.testtask.entity.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class RecipeService implements DAO<Recipe> {
    Connection connection;
    PreparedStatement preparedStatement = null;

    @Override
    public void add(Recipe recipe) {
        connection = ConnectionManager.getConnection();
        String sql = "INSERT INTO RECIPE(DESCRIPTION,PATIENT,DOCTOR,CREATEDATA,VALIDATE,PRIORITY)VALUES(?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, recipe.getDescription());
            preparedStatement.setLong(2, recipe.getPatient().getId());
            preparedStatement.setLong(3, recipe.getDoctor().getId());
            preparedStatement.setObject(4, recipe.getCreateDate());
            preparedStatement.setObject(5, recipe.getValidate());
            preparedStatement.setString(6, String.valueOf(recipe.getPriority()));
            preparedStatement.executeUpdate();
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


    }

    @Override
    public List<Recipe> getAll() {
        connection = ConnectionManager.getConnection();
        List<Recipe> recipes = new LinkedList<>();
        String sql = "SELECT * FROM RECIPE INNER JOIN PATIENT ON RECIPE.PATIENT = PATIENT.ID INNER JOIN DOCTOR ON RECIPE.DOCTOR = DOCTOR.ID";
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getLong("id"));
                fillingInnTheRecipe(recipe, resultSet);
                recipes.add(recipe);
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

        return recipes;
    }

    @Override
    public Recipe getbyId(long id) throws SQLException {
        connection = ConnectionManager.getConnection();
        Recipe recipe = new Recipe();
        String sql = "SELECT id,description,patient,doctor,createdata,validate,priority FROM Recipe WHERE ID=?";
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        recipe.setId(resultSet.getLong("id"));
        fillingInnTheRecipe(recipe, resultSet);
        return recipe;
    }

    public List<Recipe> findByPriority(Priority priority) {
        connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM RECIPE INNER JOIN PATIENT ON RECIPE.PATIENT = PATIENT.ID INNER JOIN DOCTOR ON RECIPE.DOCTOR = DOCTOR.ID WHERE PRIORITY LIKE ?";
        List<Recipe> recipes = new LinkedList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, priority.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getLong("id"));
                fillingInnTheRecipe(recipe, resultSet);
                recipes.add(recipe);
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
        return recipes;

    }

    public List<Recipe> findByDescription(String description) {
        connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM RECIPE INNER JOIN PATIENT ON RECIPE.PATIENT = PATIENT.ID INNER JOIN DOCTOR ON RECIPE.DOCTOR = DOCTOR.ID WHERE DESCRIPTION LIKE ?";
        List<Recipe> recipes = new LinkedList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, description);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getLong("id"));
                fillingInnTheRecipe(recipe, resultSet);
                recipes.add(recipe);
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
        return recipes;
    }

    public List<Recipe> findByPatient(Patient patient) {
        connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM RECIPE INNER JOIN PATIENT ON RECIPE.PATIENT = PATIENT.ID INNER JOIN DOCTOR ON RECIPE.DOCTOR = DOCTOR.ID WHERE PATIENT.ID = ?";
        List<Recipe> recipes = new LinkedList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, patient.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getLong("id"));
                fillingInnTheRecipe(recipe, resultSet);
                recipes.add(recipe);
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
        return recipes;
    }

    public List<Recipe> findByDoctor(Doctor doctor) {
        connection = ConnectionManager.getConnection();
        String sql = "SELECT * FROM RECIPE INNER JOIN PATIENT ON RECIPE.PATIENT = PATIENT.ID INNER JOIN DOCTOR ON RECIPE.DOCTOR = DOCTOR.ID WHERE DOCTOR.ID = ?";
        List<Recipe> recipes = new LinkedList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, doctor.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getLong("id"));
                fillingInnTheRecipe(recipe, resultSet);
                recipes.add(recipe);
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
        return recipes;
    }


    @Override
    public void update(Recipe recipe) {
        connection = ConnectionManager.getConnection();
        String sql = "UPDATE RECIPE SET DESCRIPTION=?,PATIENT=?,DOCTOR=?,CREATEDATA=?,VALIDATE=?,PRIORITY=? where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, recipe.getDescription());
            preparedStatement.setLong(2, recipe.getPatient().getId());
            preparedStatement.setLong(3, recipe.getDoctor().getId());
            preparedStatement.setObject(4, recipe.getCreateDate());
            preparedStatement.setObject(5, recipe.getValidate());
            preparedStatement.setString(6, String.valueOf(recipe.getPriority()));
            preparedStatement.setLong(7, recipe.getId());
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
    public void remove(Recipe recipe) {
        String sql = "DELETE FROM Recipe where id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, recipe.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null || preparedStatement != null) {
                try {
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    private Patient fillingInnThePatient(Patient patient, ResultSet resultSet) throws SQLException {
        patient = new Patient(resultSet.getLong("PATIENT.ID"),
                resultSet.getString("PATIENT.FIRSTNAME"),
                resultSet.getString("PATIENT.LASTNAME"),
                resultSet.getString("PATIENT.SECONDNAME"),
                resultSet.getString("PATIENT.PHONE"));


        return patient;
    }

    private Doctor fillingInnTheDoctor(Doctor doctor, ResultSet resultSet) throws SQLException {
        doctor = new Doctor(resultSet.getLong("DOCTOR.ID"),
                resultSet.getString("DOCTOR.FIRSTNAME"),
                resultSet.getString("DOCTOR.LASTNAME"),
                resultSet.getString("DOCTOR.SECONDNAME"),
                resultSet.getString("DOCTOR.SPECIALIZATION"));
        return doctor;
    }

    private Recipe fillingInnTheRecipe(Recipe recipe, ResultSet resultSet) throws SQLException {
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        recipe.setCreateDate(resultSet.getObject("createdata", LocalDate.class));
        recipe.setDescription(resultSet.getString("description"));
        recipe.setDoctor(fillingInnTheDoctor(doctor, resultSet));
        recipe.setPatient(fillingInnThePatient(patient, resultSet));
        recipe.setPriority(Priority.valueOf(resultSet.getString("priority")));
        recipe.setValidate(resultSet.getObject("validate", LocalDate.class));
        return recipe;
    }
}
