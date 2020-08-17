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

    private PatientService patientService = new PatientService();;
    private final TextField firstName;
    private final TextField secondName;
    private final TextField lastName;
    private final TextField phone;
    private final Button delete;
    private final Button edit;


    public PatientLayout(Patient patient, ChangeListener changeListener){
        Patient upPatient = new Patient();
        setWidth("100%");
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        firstName =  new TextField();
        secondName = new TextField();
        lastName = new TextField();
        phone = new TextField();
        delete = new Button(VaadinIcons.MINUS);
        edit = new Button("Edit");

        firstName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);firstName.setValueChangeMode(ValueChangeMode.BLUR);
        secondName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);secondName.setValueChangeMode(ValueChangeMode.BLUR);
        lastName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);lastName.setValueChangeMode(ValueChangeMode.BLUR);
        phone.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);phone.setValueChangeMode(ValueChangeMode.BLUR);
        delete.addClickListener(e->{
            try {
                patient.setSelect(true);
                patientService.remove(patient);
                changeListener.changed(patient);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });delete.setWidth("50");
        edit.addClickListener(e -> {

            VerticalLayout subContent = new VerticalLayout();
            Window editDocotor = new Window();
            TextField firstName = new TextField("Firstname");
            firstName.setValue(patient.getFirstName());

            TextField secondName = new TextField("Secondname");
            secondName.setValue(patient.getSecondName());

            TextField lastName = new TextField("Lastname");
            lastName.setValue(patient.getLastName());
            TextField Phone = new TextField("Specialization");
            Phone.setValue(patient.getPhone());

            Button update = new Button("Update");
            Button close = new Button("Close");
            close.addClickListener(o->{
                editDocotor.close();
            });
            update.addClickListener(u->{
                try {
                    upPatient.setFirstName(firstName.getValue());
                    upPatient.setSecondName(secondName.getValue());
                    upPatient.setLastName(lastName.getValue());
                    upPatient.setPhone(Phone.getValue());
                    upPatient.setId(patient.getId());
                    upPatient.setSelect(true);
                    patientService.update(upPatient);
                    changeListener.changed(upPatient);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                editDocotor.close();
            });
            update.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            subContent.addComponents(firstName,secondName,lastName,Phone,update);
            editDocotor.setContent(subContent);
            editDocotor.setModal(true);
            UI.getCurrent().addWindow(editDocotor);
        });
        Binder<Patient> binder = new Binder<>(Patient.class);
        binder.bind(firstName,Patient::getFirstName,Patient::setFirstName);
        binder.bind(secondName,Patient::getSecondName,Patient::setSecondName);
        binder.bind(lastName,Patient::getLastName,Patient::setLastName);
        binder.bind(phone,Patient::getPhone,Patient::setPhone);
        binder.setBean(patient);
        addComponentsAndExpand(firstName,secondName,lastName,phone,edit,delete);

        binder.addValueChangeListener(e-> {
            try {
                changeListener.changed(patient);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}
