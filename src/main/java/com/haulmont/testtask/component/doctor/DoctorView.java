package com.haulmont.testtask.component.doctor;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.service.DoctorService;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

@Theme("valo")
public class DoctorView extends VerticalLayout implements View {
    private VerticalLayout layout;
    private DoctorList doctorList = new DoctorList();


    public DoctorView() {
        MenuBar menuBar = new MenuBar();
        menuBar.setStyleName("Light");
        menuBar.addItem("Patient", (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("patient"));
        menuBar.addItem("Doctor", (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("doctor"));
        menuBar.getItems().get(1).setEnabled(false);
        menuBar.addItem("Recipe", (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("recipe"));
        setMargin(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(menuBar);
        setupLayout();
        addHeader();
        addDoctorList();
        addActionButtons();
        doctorList.update();
    }


    private void setupLayout() {
        layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(layout);
    }

    private void addHeader() {
        Label header = new Label("Doctor list");
        header.addStyleName(ValoTheme.LABEL_H1);
        layout.addComponent(header);
    }

    private void addDoctorList() {
        layout.addComponent(doctorList);
    }

    private void addActionButtons() {
        HorizontalLayout formLayout = new HorizontalLayout();
        Button create = new Button("create");
        create.addClickListener(e -> {
            Button add = new Button("Add");
            Window doctorAddWindow = new Window("New Doctor");
            VerticalLayout subContent = new VerticalLayout();
            TextField firstname = new TextField("First Name");
            firstname.setMaxLength(45);
            TextField secondName = new TextField("Second Name");
            secondName.setMaxLength(45);
            TextField lastName = new TextField("Last Name");
            lastName.setMaxLength(45);
            TextField specializiation = new TextField("Specialization");
            specializiation.setMaxLength(45);
            add.addClickListener(a -> {
                Doctor doctor = new Doctor(firstname.getValue(), secondName.getValue(), lastName.getValue(), specializiation.getValue());
                if (validate(firstname, secondName, lastName, specializiation)) {
                    try {
                        doctorList.addDoctor(doctor);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    doctorAddWindow.close();
                }
            });
            HorizontalLayout layout = new HorizontalLayout();
            Button close = new Button("Close");
            close.addClickListener(clo -> {
                doctorAddWindow.close();
            });
            layout.addComponents(add, close);
            subContent.addComponents(firstname, secondName, lastName, specializiation, layout);
            doctorAddWindow.setContent(subContent);
            doctorAddWindow.setModal(true);
            UI.getCurrent().addWindow(doctorAddWindow);
            add.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            close.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        });
        TextField taskField = new TextField();
        taskField.focus();
        layout.addComponent(create);

    }

    private static boolean validate(TextField firstName, TextField secondName, TextField lastName, TextField specialization) {
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
        if (specialization.getValue() == null || specialization.getValue().isEmpty()) {
            specialization.setStyleName("error");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
