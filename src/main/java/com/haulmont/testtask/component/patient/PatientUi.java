package com.haulmont.testtask.component.patient;

import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.service.DoctorService;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

@Theme("valo")
public class PatientUi extends UI implements View {
    public static final String NAME = "Patient";
    private VerticalLayout layout;
    PatientList patientList;
    private DoctorService doctorService;

    @Override
    protected void init(VaadinRequest request) {
        setupLayout();
        addHeader();
        addForm();
        addDoctorList();
        addActionButtons();
    }


    private void setupLayout() {
        layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);
    }

    private void addHeader() {
        Label header = new Label("Patient why");
        header.addStyleName(ValoTheme.LABEL_H1);
        layout.addComponent(header);
    }

    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("80%");
        Button create = new Button("create");
        create.addClickListener(e -> {
            Button add = new Button("Add");
            Window newPatient = new Window("New Patient");
            VerticalLayout subContent = new VerticalLayout();
            TextField firstname = new TextField("First Name");
            TextField secondName = new TextField("Second Name");
            TextField lastName = new TextField("Last Name");
            TextField phone = new TextField("Phone");
            add.addClickListener(a -> {
                Patient patient = new Patient(firstname.getValue(), secondName.getValue(), lastName.getValue(), phone.getValue());
                try {
                    patientList.addPatient(patient);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                newPatient.close();
            });
            subContent.addComponents(firstname, secondName, lastName, phone, add);
            newPatient.setContent(subContent);
            newPatient.setModal(true);
            addWindow(newPatient);
            add.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        });
        TextField taskField = new TextField();
        taskField.focus();
        layout.addComponents(create);
    }

    private void addDoctorList() {
        layout.addComponent(patientList);
    }

    private void addActionButtons() {
        Button deleteButton = new Button("Delete selected items");

        deleteButton.addClickListener(click -> {
            try {
                patientList.deleteCompleted();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        layout.addComponent(deleteButton);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
