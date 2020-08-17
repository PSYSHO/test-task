package com.haulmont.testtask.component;

import java.sql.SQLException;

public interface ChangeListener<E> {
    void changed(E e) throws SQLException;
}
