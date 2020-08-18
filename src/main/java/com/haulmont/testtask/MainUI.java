package com.haulmont.testtask;

import com.haulmont.testtask.component.doctor.DoctorView;
import com.haulmont.testtask.component.patient.PatientView;
import com.haulmont.testtask.component.recipe.RecipeView;
import com.haulmont.testtask.service.ConnectionManager;
import com.haulmont.testtask.service.DoctorService;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo")
public class MainUI extends UI {

    public static Navigator navigator;

    public DoctorService doctorService = new DoctorService();

    public static final String PATIENT = "patient";

    public static final String DOCTOR = "doctor";

    public static final String RECIPE = "recipe";
    public boolean check = false;

    @Override
    protected void init(VaadinRequest request) {
        ConnectionManager.createDB();
        if(doctorService.getAll().isEmpty()){
            ConnectionManager.fillingData();
        }
        navigator = new Navigator(this, this);
        navigator.addView("", new StartView());
        navigator.addView(PATIENT, new PatientView());
        navigator.addView(DOCTOR, new DoctorView());
        navigator.addView(RECIPE, new RecipeView());
    }

}

class StartView extends VerticalLayout implements View {
    public StartView() {
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Patient",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("patient"));
        menuBar.addItem("Doctor",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("doctor"));
        menuBar.addItem("Recipe",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo("recipe"));
        setMargin(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(menuBar);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}