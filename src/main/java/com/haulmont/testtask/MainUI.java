package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    //private PatientUi patientUi;
    private VerticalLayout verticalLayout = new VerticalLayout();
    private Button getPatientUI = new Button("Manage Patient");
    private Button getDoctorUI = new Button("Manage Doctor");
    private Button getRecipeUI = new Button("Manage Recipe");
    private Label label = new Label("Welcome to the test TASK");


    @Override
    protected void init(VaadinRequest request) {
        setVerticalLayout();
    }
    private void setVerticalLayout(){
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        label.addStyleName(ValoTheme.LABEL_H1);
        getDoctorUI.setWidth("150");getPatientUI.setWidth("150");getRecipeUI.setWidth("150");


        verticalLayout.addComponent(label);
        verticalLayout.addComponents(getDoctorUI,getPatientUI,getRecipeUI);

        setContent(verticalLayout);
    }


}