package com.haulmont.testtask.component.doctor;


import com.haulmont.testtask.component.ChangeListener;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.service.DoctorService;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.List;


public class DoctorList extends VerticalLayout implements ChangeListener<Doctor> {

    DoctorService doctorService = new DoctorService();
    private List<Doctor> doctorList;

    @PostConstruct
    void init() throws SQLException {
        setWidth("80%");
        update();
    }

    @Override
    public void changed(Doctor doctor) throws SQLException {
        if (doctor.getSelect()) {
            doctor.setSelect(false);
            update();
        } else doctorList.add(doctor);
    }

    void update() throws SQLException {
        setTodos(doctorService.getAll());

    }

    private void setTodos(List<Doctor> doctors) {
        this.doctorList = doctors;
        removeAllComponents();
        doctorList.forEach(doctor -> addComponent(new DoctorLayout(doctor, this)));
    }


    void addDoctor(Doctor doctor) throws SQLException {
        doctorService.add(doctor);
        update();
    }


    public void remove() {
    }
}
