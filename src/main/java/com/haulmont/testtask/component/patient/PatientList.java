package com.haulmont.testtask.component.patient;


import com.haulmont.testtask.component.ChangeListener;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.service.PatientService;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

import java.sql.SQLException;


public class PatientList extends VerticalLayout implements ChangeListener<Patient> {
    PatientService patientService = new PatientService();

    private List<Patient> patients;

    public PatientList() {
    }

    @Override
    public void changed(Patient patient) throws SQLException {
        if (patient.getSelect()){
            patient.setSelect(false);
            update();
        }
        else patients.add(patient);
    }

    void update() throws SQLException {
        setPatient(patientService.getAll());
    }

    private void setPatient(List<Patient> patients) {
        this.patients = patients;
        removeAllComponents();
        patients.forEach(patient -> addComponent(new PatientLayout(patient, this)));
    }


    public void addPatient(Patient patient) throws SQLException {
        patientService.add(patient);
        update();
    }

}
