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
    public static final String NAME = "Patient";
    private VerticalLayout layout;
    private PatientList patientList = new PatientList();
    HorizontalLayout formLayout = new HorizontalLayout();

    public PatientView() {
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Пациенты", (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("doctor"));
        menuBar.getItems().get(0).setEnabled(false);
        menuBar.addItem("Доктора",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("doctor"));
        menuBar.addItem("Рецепты",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("recipe"));
        setMargin(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(menuBar);
        setupLayout();
        addHeader();
        addPatientList();
        addActionButtons();

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
            HorizontalLayout layout = new HorizontalLayout();
            Button close = new Button("Close");
            layout.addComponents(add,close);
            subContent.addComponents(firstname, secondName, lastName, phone, layout);
            newPatient.setContent(subContent);
            newPatient.setModal(true);
            close.addClickListener(c->{
                newPatient.close();
            });
            add.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            UI.getCurrent().addWindow(newPatient);
        });

        layout.addComponent(create);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
