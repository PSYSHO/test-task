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
    private final Button delete;
    private final Button edit;
    Button update = new Button("Update");
    private final Button statistics;


    public DoctorLayout(Doctor doctor, ChangeListener changeListener) {
        edit = new Button("Edit");
        delete = new Button(VaadinIcons.MINUS);
        statistics = new Button("Statistics");
        Doctor upDoctor = new Doctor();
        firstName = new TextField();
        secondName = new TextField();
        lastName = new TextField();
        specialization = new TextField();
        firstName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        firstName.setValueChangeMode(ValueChangeMode.BLUR);
        firstName.setMaxLength(45);
        secondName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        secondName.setValueChangeMode(ValueChangeMode.BLUR);
        secondName.setMaxLength(45);
        lastName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        lastName.setValueChangeMode(ValueChangeMode.BLUR);
        lastName.setMaxLength(45);
        specialization.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        specialization.setValueChangeMode(ValueChangeMode.BLUR);
        specialization.setMaxLength(45);
        setWidth("100%");
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        delete.addClickListener(e -> {
            if (doctorService.remove(doctor)) {
                doctor.setSelect(true);
                changeListener.changed(doctor);
            } else Notification.show("Doctor prescribed recipe");
        });

        delete.setWidth("50px");
        edit.addClickListener(e -> {
            VerticalLayout subContent = new VerticalLayout();
            Window editDocotor = new Window();
            TextField nfirstName = new TextField("Firstname");
            nfirstName.setMaxLength(45);
            nfirstName.setValue(doctor.getFirstName());

            TextField nsecondName = new TextField("Secondname");
            nsecondName.setValue(doctor.getSecondName());
            nsecondName.setMaxLength(45);

            TextField nlastName = new TextField("Lastname");
            nlastName.setValue(doctor.getLastName());
            nlastName.setMaxLength(45);
            TextField nspecialization = new TextField("Specialization");
            nspecialization.setValue(doctor.getSpecialization());
            nspecialization.setMaxLength(45);



            update.addClickListener(u -> {
                if (validate(nfirstName, nsecondName, nlastName, nspecialization)) {
                    upDoctor.setFirstName(nfirstName.getValue());
                    upDoctor.setSecondName(nsecondName.getValue());
                    upDoctor.setLastName(nlastName.getValue());
                    upDoctor.setSpecialization(nspecialization.getValue());
                    upDoctor.setId(doctor.getId());
                    upDoctor.setSelect(true);
                    doctorService.update(upDoctor);
                    changeListener.changed(upDoctor);
                }
                editDocotor.close();

            });
            update.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            subContent.addComponents(nfirstName, nsecondName, nlastName, nspecialization, update);
            editDocotor.setContent(subContent);
            editDocotor.setModal(true);
            UI.getCurrent().addWindow(editDocotor);
        });
        statistics.addClickListener(stat ->
        {
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
            normal.setValue("Normal recipes" + " " + normalRecipe);
            Label cito = new Label(ValoTheme.LABEL_H3);
            cito.setValue("Cito recipes" + " " + citoRecipe);
            Label statim = new Label(ValoTheme.LABEL_H3);
            statim.setValue("Statim recipes" + " " + statimRecipe);
            Button close = new Button("Close");
            statW.setModal(true);
            close.addClickListener(c -> {
                statW.close();
            });
            layout.addComponents(label, normal, cito, statim, close);
            statW.setContent(layout);
            UI.getCurrent().addWindow(statW);
        });
        Binder<Doctor> binder = new Binder<>(Doctor.class);
        binder.bind(firstName, Doctor::getFirstName, Doctor::setFirstName);
        binder.bind(secondName, Doctor::getSecondName, Doctor::setFirstName);
        binder.bind(lastName, Doctor::getLastName, Doctor::setLastName);
        binder.bind(specialization, Doctor::getSpecialization, Doctor::setSpecialization);
        binder.setBean(doctor);
        addComponents(firstName, secondName, lastName, specialization ,edit,delete,statistics);
        binder.addValueChangeListener(e ->
        {
            changeListener.changed(doctor);
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


