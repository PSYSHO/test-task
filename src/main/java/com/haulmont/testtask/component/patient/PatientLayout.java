package com.haulmont.testtask.component.patient;

import com.haulmont.testtask.component.ChangeListener;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.service.PatientService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

public class PatientLayout extends HorizontalLayout {

    private PatientService patientService = new PatientService();
    private static TextField firstName;
    private static TextField secondName;
    private static TextField lastName;
    private static TextField phone;
    private final Button delete;
    private final Button edit;


    public PatientLayout(Patient patient, ChangeListener changeListener) {
        Patient upPatient = new Patient();
        setWidth("100%");
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        firstName = new TextField();
        secondName = new TextField();
        lastName = new TextField();
        phone = new TextField();
        delete = new Button(VaadinIcons.MINUS);
        edit = new Button("Edit");

        firstName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        firstName.setValueChangeMode(ValueChangeMode.BLUR);
        firstName.setMaxLength(45);
        secondName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        secondName.setValueChangeMode(ValueChangeMode.BLUR);
        secondName.setMaxLength(45);
        lastName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        lastName.setValueChangeMode(ValueChangeMode.BLUR);
        lastName.setMaxLength(45);
        phone.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        phone.setValueChangeMode(ValueChangeMode.BLUR);
        phone.setMaxLength(45);
        delete.addClickListener(e -> {
            if (patientService.remove(patient)) {
                patient.setSelect(true);
                changeListener.changed(patient);
            } else Notification.show("Patient has a prescription");

        });
        delete.setWidth("50");
        edit.addClickListener(e -> {

            VerticalLayout subContent = new VerticalLayout();
            Window editPatient = new Window();
            TextField firstName = new TextField("Firstname");
            firstName.setValue(patient.getFirstName());
            firstName.setMaxLength(45);

            TextField secondName = new TextField("Secondname");
            secondName.setValue(patient.getSecondName());
            secondName.setMaxLength(45);

            TextField lastName = new TextField("Lastname");
            lastName.setValue(patient.getLastName());
            lastName.setMaxLength(45);

            TextField Phone = new TextField("Specialization");
            Phone.setValue(patient.getPhone());
            phone.setMaxLength(45);

            Button update = new Button("Update");
            Button close = new Button("Close");
            close.addClickListener(o -> {
                editPatient.close();
            });
            update.addClickListener(u -> {
                if (validate(firstName, secondName, lastName, phone)) {
                    upPatient.setFirstName(firstName.getValue());
                    upPatient.setSecondName(secondName.getValue());
                    upPatient.setLastName(lastName.getValue());
                    upPatient.setPhone(Phone.getValue());
                    upPatient.setId(patient.getId());
                    upPatient.setSelect(true);
                    patientService.update(upPatient);
                    changeListener.changed(upPatient);
                }
                editPatient.close();
            });
            update.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            HorizontalLayout layout = new HorizontalLayout();
            layout.addComponents(update, close);
            subContent.addComponents(firstName, secondName, lastName, Phone, layout);
            editPatient.setContent(subContent);
            editPatient.setModal(true);
            UI.getCurrent().addWindow(editPatient);
        });
        Binder<Patient> binder = new Binder<>(Patient.class);
        binder.bind(firstName, Patient::getFirstName, Patient::setFirstName);
        binder.bind(secondName, Patient::getSecondName, Patient::setSecondName);
        binder.bind(lastName, Patient::getLastName, Patient::setLastName);
        binder.bind(phone, Patient::getPhone, Patient::setPhone);
        binder.setBean(patient);
        addComponentsAndExpand(firstName, secondName, lastName, phone, edit, delete);

        binder.addValueChangeListener(e -> {
            changeListener.changed(patient);
        });
    }

    private static boolean validate(TextField firstName, TextField secondName, TextField lastName, TextField phone) {
        boolean isValid = true;
        System.out.println(firstName.getValue());
        if (firstName.getValue() == null || firstName.getValue().isEmpty()) {
            firstName.setStyleName("error");
            isValid = false;
        }
        if (secondName.getValue() == null || secondName.getValue().isEmpty()) {
            secondName.setStyleName("error");
            isValid = false;
        }
        if (lastName.getValue() == null || lastName.getValue().isEmpty()) {
            lastName.setStyleName("error");
            isValid = false;
        }
        if (phone.getValue() == null || phone.getValue().isEmpty()) {
            phone.setStyleName("error");
            isValid = false;
        }
        return isValid;
    }
}
