package com.haulmont.testtask.component.doctor;

import com.haulmont.testtask.component.ChangeListener;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.service.DoctorService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


import java.sql.SQLException;

public class DoctorLayout extends HorizontalLayout {

    private DoctorService doctorService = new DoctorService();

    private final TextField firstName;
    private final TextField secondName;
    private final TextField lastName;
    private final TextField specialization;
    private final Button delete;
    private final Button edit;


    public DoctorLayout(Doctor doctor, ChangeListener changeListener) {
        Doctor upDoctor = new Doctor();
        setWidth("100%");
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        edit = new Button("Edit");
        firstName = new TextField();
        secondName = new TextField();
        lastName = new TextField();
        specialization = new TextField();
        delete = new Button(VaadinIcons.MINUS);
        delete.addClickListener(e -> {
            try {
                doctor.setSelect(true);
                doctorService.remove(doctor);
                changeListener.changed(doctor);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        delete.setWidth("50px");
        edit.addClickListener(e -> {

            VerticalLayout subContent = new VerticalLayout();
            Window editDocotor = new Window();
            TextField firstName = new TextField("Firstname");
            firstName.setValue(doctor.getFirstName());

            TextField secondName = new TextField("Secondname");
            secondName.setValue(doctor.getSecondName());

            TextField lastName = new TextField("Lastname");
            lastName.setValue(doctor.getLastName());
            TextField specialization = new TextField("Specialization");
            specialization.setValue(doctor.getSpecialization());

            Button update = new Button("Update");

            update.addClickListener(u->{
                try {
                    upDoctor.setFirstName(firstName.getValue());
                    upDoctor.setSecondName(secondName.getValue());
                    upDoctor.setLastName(lastName.getValue());
                    upDoctor.setSpecialization(specialization.getValue());
                    upDoctor.setId(doctor.getId());
                    upDoctor.setSelect(true);
                    doctorService.update(upDoctor);
                    changeListener.changed(upDoctor);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                editDocotor.close();
            });
            update.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            subContent.addComponents(firstName,secondName,lastName,specialization,update);
            editDocotor.setContent(subContent);
            editDocotor.setModal(true);
            UI.getCurrent().addWindow(editDocotor);
        });

        firstName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        firstName.setValueChangeMode(ValueChangeMode.BLUR);
        secondName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        secondName.setValueChangeMode(ValueChangeMode.BLUR);
        lastName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        lastName.setValueChangeMode(ValueChangeMode.BLUR);
        specialization.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        specialization.setValueChangeMode(ValueChangeMode.BLUR);
        Binder<Doctor> binder = new Binder<>(Doctor.class);
        binder.bind(firstName, Doctor::getFirstName, Doctor::setFirstName);
        binder.bind(secondName, Doctor::getSecondName, Doctor::setFirstName);
        binder.bind(lastName, Doctor::getLastName, Doctor::setLastName);
        binder.bind(specialization, Doctor::getSpecialization, Doctor::setSpecialization);
        binder.setBean(doctor);
        addComponentsAndExpand(firstName, secondName, lastName, specialization, edit, delete);
        binder.addValueChangeListener(e -> {
            try {
                changeListener.changed(doctor);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

}
