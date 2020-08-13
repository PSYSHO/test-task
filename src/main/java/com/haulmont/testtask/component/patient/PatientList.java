package com.haulmont.testtask.component.patient;


import com.haulmont.testtask.dao.ChangeListener;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.service.PatientService;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

import java.sql.SQLException;


public class PatientList extends VerticalLayout implements ChangeListener<Patient> {
    PatientService patientService;

    private List<Patient> patients;

    @Override
    public void changed(Patient patient) throws SQLException {
    patients.add(patient);
    }
    void update() throws SQLException {
        setTodos(patientService.getAll());
    }

    private void setTodos(List<Patient> patients) {
        this.patients=patients;
        removeAllComponents();
        patients.forEach(patient -> addComponent(new PatientLayout(patient,this)));
    }


    void addPatient(Patient patient) throws SQLException {
        patientService.add(patient);
        update();
    }
    public void deleteCompleted() throws SQLException {
        patients.forEach(patient -> {
            if(patient.getSelect()){
                try {
                    patientService.remove(patient);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        update();
    }
}
