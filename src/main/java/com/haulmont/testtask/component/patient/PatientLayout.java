package com.haulmont.testtask.component.patient;

import com.haulmont.testtask.dao.ChangeListener;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.service.PatientService;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

public class PatientLayout extends HorizontalLayout {

    private PatientService patientService;
    private PatientList patientList;
    private final CheckBox done;
    private final TextField firstName;
    private final TextField secondName;
    private final TextField lastName;
    private final TextField phone;
    private final Button delete;

    public PatientLayout(Patient patient, ChangeListener changeListener){
        setWidth("100%");
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        done = new CheckBox();
        firstName =  new TextField();
        secondName = new TextField();
        lastName = new TextField();
        phone = new TextField();
        delete = new Button(VaadinIcons.MINUS);

        firstName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);firstName.setValueChangeMode(ValueChangeMode.BLUR);
        secondName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);secondName.setValueChangeMode(ValueChangeMode.BLUR);
        lastName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);lastName.setValueChangeMode(ValueChangeMode.BLUR);
        phone.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);phone.setValueChangeMode(ValueChangeMode.BLUR);
        Binder<Patient> binder = new Binder<>(Patient.class);
        binder.bind(firstName,Patient::getFirstName,Patient::setFirstName);
        binder.bind(secondName,Patient::getSecondName,Patient::setSecondName);
        binder.bind(lastName,Patient::getLastName,Patient::setLastName);
        binder.bind(phone,Patient::getPhone,Patient::setPhone);
        binder.bind(done,Patient::getSelect,Patient::setSelect);
        binder.setBean(patient);
        addComponent(done);
        addComponentsAndExpand(firstName,secondName,lastName,phone);
        binder.addValueChangeListener(e-> {
            try {
                changeListener.changed(patient);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}
