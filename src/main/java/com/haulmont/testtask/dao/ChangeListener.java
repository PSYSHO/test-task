package com.haulmont.testtask.dao;

import java.sql.SQLException;

public interface ChangeListener<E> {
    void changed(E e) throws SQLException;
}
