package com.haulmont.testtask.component.patient;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.service.DoctorService;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;


@Theme("valo")
public class PatientView extends VerticalLayout implements View {
    private VerticalLayout layout;
    private PatientList patientList = new PatientList();
    HorizontalLayout formLayout = new HorizontalLayout();

    public PatientView() {
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Patient", (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("doctor"));
        menuBar.getItems().get(0).setEnabled(false);
        menuBar.addItem("Doctor",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("doctor"));
        menuBar.addItem("Recipe",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("recipe"));
        setMargin(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(menuBar);
        setupLayout();
        addHeader();
        addPatientList();
        addActionButtons();
        patientList.update();
    }


    private void setupLayout() {
        layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(layout);
    }

    private void addHeader() {
        Label header = new Label("Patient list");
        header.addStyleName(ValoTheme.LABEL_H1);
        layout.addComponent(header);
    }


    private void addPatientList() {
        layout.addComponent(patientList);
    }

    private void addActionButtons() throws NullPointerException {
        formLayout.setWidth("80%");
        Button create = new Button("create");
        create.addClickListener(e -> {
            Button add = new Button("Add");
            Window newPatient = new Window("New Patient");
            VerticalLayout subContent = new VerticalLayout();
            TextField firstname = new TextField("First Name");
            firstname.setMaxLength(45);
            TextField secondName = new TextField("Second Name");
            secondName.setMaxLength(45);
            TextField lastName = new TextField("Last Name");
            lastName.setMaxLength(45);
            TextField phone = new TextField("Phone");
            phone.setMaxLength(45);
            add.addClickListener(a -> {
                Patient patient = new Patient(firstname.getValue(), secondName.getValue(), lastName.getValue(), phone.getValue());
                if (validate(firstname, secondName, lastName, phone)) {
                    try {
                        patientList.addPatient(patient);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    newPatient.close();
                }
            });
            HorizontalLayout layout = new HorizontalLayout();
            Button close = new Button("Close");
            layout.addComponents(add, close);
            subContent.addComponents(firstname, secondName, lastName, phone, layout);
            newPatient.setContent(subContent);
            newPatient.setModal(true);
            close.addClickListener(c -> {
                newPatient.close();
            });
            add.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            UI.getCurrent().addWindow(newPatient);
        });

        layout.addComponent(create);
    }

    private static boolean validate(TextField firstName, TextField secondName, TextField lastName, TextField phone) {
        boolean isValid = true;
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
