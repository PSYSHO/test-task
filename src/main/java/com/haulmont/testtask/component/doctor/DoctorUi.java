package com.haulmont.testtask.component.doctor;

import com.haulmont.testtask.entity.Doctor;
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
public class DoctorUi extends UI implements View {
    private VerticalLayout layout;
    DoctorList doctorList;

    private DoctorService doctorService;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupLayout();
        addHeader();
        addForm();
        addDoctorList();
        addActionButtons();
    }

    private void setupLayout() {
        Button button = new Button("Patient");
        button.addClickListener(e -> {
            getUI().getNavigator().navigateTo("/p");
        });
        layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(button);
        setContent(layout);
    }

    private void addHeader() {
        Label header = new Label("DOCTOR WHO");
        header.addStyleName(ValoTheme.LABEL_H1);
        layout.addComponent(header);
    }

    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("80%");
        Button create = new Button("create");
        create.addClickListener(e -> {
            Button add = new Button("Add");
            Window doctorAddWindow = new Window("New Doctor");
            VerticalLayout subContent = new VerticalLayout();
            TextField firstname = new TextField("First Name");
            TextField secondName = new TextField("Second Name");
            TextField lastName = new TextField("Last Name");
            TextField specializiation = new TextField("Specialization");
            add.addClickListener(a -> {
                Doctor doctor = new Doctor(firstname.getValue(), secondName.getValue(), lastName.getValue(), specializiation.getValue());
                try {
                    doctorList.addDoctor(doctor);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                doctorAddWindow.close();
            });
            subContent.addComponents(firstname, secondName, lastName, specializiation, add);
            doctorAddWindow.setContent(subContent);
            doctorAddWindow.setModal(true);
            addWindow(doctorAddWindow);
            add.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        });
        TextField taskField = new TextField();
        taskField.focus();
        layout.addComponents(create);
    }

    private void addDoctorList() {
        layout.addComponent(doctorList);
    }

    private void addActionButtons() {
        Button deleteButton = new Button("Delete selected items");

        deleteButton.addClickListener(click -> {
            try {
                doctorList.deleteCompleted();
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
