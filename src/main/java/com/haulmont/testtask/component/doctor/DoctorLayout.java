package com.haulmont.testtask.component.doctor;

import com.haulmont.testtask.dao.ChangeListener;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.service.DoctorService;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


import java.sql.SQLException;

public class DoctorLayout extends HorizontalLayout {

    private DoctorService doctorService;

    private final CheckBox done;
    private final TextField firstName;
    private final TextField secondName;
    private final TextField lastName;
    private final TextField specialization;
    private final Button delete;



    public DoctorLayout(Doctor doctor, ChangeListener changeListener) {
        setWidth("100%");
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        done = new CheckBox();
        firstName =  new TextField();
        secondName = new TextField();
        lastName = new TextField();
        specialization = new TextField();
        delete = new Button(VaadinIcons.MINUS);

        firstName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);firstName.setValueChangeMode(ValueChangeMode.BLUR);
        secondName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);secondName.setValueChangeMode(ValueChangeMode.BLUR);
        lastName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);lastName.setValueChangeMode(ValueChangeMode.BLUR);
        specialization.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);specialization.setValueChangeMode(ValueChangeMode.BLUR);
        Binder<Doctor> binder = new Binder<>(Doctor.class);
        binder.bind(firstName,Doctor::getFirstName,Doctor::setFirstName);
        binder.bind(secondName,Doctor::getSecondName,Doctor::setFirstName);
        binder.bind(lastName,Doctor::getLastName,Doctor::setLastName);
        binder.bind(specialization,Doctor::getSpecialization,Doctor::setSpecialization);
        binder.bind(done,Doctor::getSelect,Doctor::setSelect);
        binder.setBean(doctor);
        addComponent(done);
        addComponentsAndExpand(firstName,secondName,lastName,specialization);
        binder.addValueChangeListener(e-> {
            try {
                changeListener.changed(doctor);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

}
