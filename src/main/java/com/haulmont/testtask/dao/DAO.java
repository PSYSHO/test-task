package com.haulmont.testtask.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    void add(T obj) throws SQLException;
    List<T> getAll() throws SQLException;
    T getbyId(long id) throws SQLException;
    void update(T obj) throws SQLException;
    void remove(T obj) throws SQLException;
}
