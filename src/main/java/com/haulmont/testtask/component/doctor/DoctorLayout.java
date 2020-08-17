package com.haulmont.testtask.component.doctor;

import com.haulmont.testtask.component.ChangeListener;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.entity.Priority;
import com.haulmont.testtask.entity.Recipe;
import com.haulmont.testtask.service.DoctorService;
import com.haulmont.testtask.service.RecipeService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;


import java.sql.SQLException;

public class DoctorLayout extends HorizontalLayout {

    private DoctorService doctorService = new DoctorService();
    private static TextField firstName;
    private static TextField secondName;
    private static TextField lastName;
    private static TextField specialization;
    private static Button delete = new Button(VaadinIcons.MINUS);
    private static Button edit = new Button("Edit");
    private static Button statistics = new Button("Statistics");


    public DoctorLayout(Doctor doctor, ChangeListener changeListener) {
        firstName = new TextField();
        secondName = new TextField();
        lastName = new TextField();
        specialization = new TextField();

        Doctor upDoctor = new Doctor();
        setWidth("100%");
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        delete.addClickListener(e -> {
            try {
                if (doctorService.remove(doctor)) {
                    doctor.setSelect(true);
                    changeListener.changed(doctor);
                } else Notification.show("Doctor prescribed recipe");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        delete.setWidth("50px");
        edit.addClickListener(e -> {

            VerticalLayout subContent = new VerticalLayout();
            Window editDocotor = new Window();
            TextField nfirstName = new TextField("Firstname");
            nfirstName.setValue(doctor.getFirstName());

            TextField nsecondName = new TextField("Secondname");
            nsecondName.setValue(doctor.getSecondName());

            TextField nlastName = new TextField("Lastname");
            nlastName.setValue(doctor.getLastName());
            TextField nspecialization = new TextField("Specialization");
            nspecialization.setValue(doctor.getSpecialization());

            Button update = new Button("Update");

            update.addClickListener(u -> {
                if (validate(nfirstName, nsecondName, nlastName, nspecialization)) {
                    upDoctor.setFirstName(nfirstName.getValue());
                    upDoctor.setSecondName(nsecondName.getValue());
                    upDoctor.setLastName(nlastName.getValue());
                    upDoctor.setSpecialization(nspecialization.getValue());
                    upDoctor.setId(doctor.getId());
                    upDoctor.setSelect(true);
                    doctorService.update(upDoctor);
                    try {
                        changeListener.changed(upDoctor);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    editDocotor.close();
                }

            });
            update.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            subContent.addComponents(nfirstName, nsecondName, nlastName, nspecialization, update);
            editDocotor.setContent(subContent);
            editDocotor.setModal(true);
            UI.getCurrent().addWindow(editDocotor);
        });
        statistics.addClickListener(stat -> {
            RecipeService recipeService = new RecipeService();
            List<Recipe> recipes = recipeService.findByDoctor(doctor);
            int citoRecipe = 0, normalRecipe = 0, statimRecipe = 0;
            for (Recipe recipe : recipes) {
                if (recipe.getPriority().equals(Priority.CITO)) {
                    citoRecipe++;
                } else if (recipe.getPriority().equals(Priority.NORMAL)) {
                    normalRecipe++;
                } else if (recipe.getPriority().equals(Priority.STATIM)) {
                    statimRecipe++;
                }
            }
            VerticalLayout layout = new VerticalLayout();
            Window statW = new Window();
            Label label = new Label(ValoTheme.LABEL_H1);
            label.setValue("Prescriptions issued");
            Label normal = new Label(ValoTheme.LABEL_H3);
            normal.setValue("Cito recipes" + " " + normalRecipe);
            Label cito = new Label(ValoTheme.LABEL_H3);
            cito.setValue("Cito recipes" + " " + citoRecipe);
            Label statim = new Label(ValoTheme.LABEL_H3);
            statim.setValue("Cito recipes" + " " + statimRecipe);
            Button close = new Button("Close");
            statW.setModal(true);
            close.addClickListener(e -> {
                statW.close();
            });
            layout.addComponents(label, normal, cito, statim, close);
            statW.setContent(layout);
            UI.getCurrent().addWindow(statW);
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
        addComponentsAndExpand(firstName, secondName, lastName, specialization, edit, delete, statistics);
        binder.addValueChangeListener(e -> {
            try {
                changeListener.changed(doctor);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

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
}


