package com.haulmont.testtask.component.recipe;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.entity.Priority;
import com.haulmont.testtask.entity.Recipe;
import com.haulmont.testtask.service.DoctorService;
import com.haulmont.testtask.service.PatientService;
import com.haulmont.testtask.service.RecipeService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.util.Locale;

public class RecipeView extends VerticalLayout implements View {
    private static final String DESCRIPTION = "Description";
    private static final String PATIENT = "Patient";
    private static final String DOCTOR = "Doctor";
    private static final String ALL = "ALL";
    private static final String PRIORITY = "Priority";
    private PatientService patientService = new PatientService();
    private DoctorService doctorService = new DoctorService();
    private RecipeService recipeService = new RecipeService();

    public RecipeView() {
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Patient",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo(MainUI.PATIENT));
        menuBar.addItem("Doctor",
                (MenuBar.Command) selectedItem -> MainUI.navigator.navigateTo(MainUI.DOCTOR));
        menuBar.addItem("Recipe", selectedItem -> MainUI.navigator.navigateTo(MainUI.RECIPE));
        menuBar.getItems().get(2).setEnabled(false);

        setMargin(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(menuBar);

        HorizontalLayout filterBox = new HorizontalLayout();
        TextField filter = new TextField();
        ComboBox<String> typeSelect = new ComboBox();
        typeSelect.setItems(PATIENT, PRIORITY, DOCTOR, DESCRIPTION, ALL);
        Button search = new Button("Search");
        Button create = new Button("Create");
        ComboBox<Patient> patientComboBox = new ComboBox<>();
        ComboBox<Doctor> doctorComboBox = new ComboBox<>();
        ComboBox<Priority> priorityComboBox = new ComboBox<>();
        Grid<Recipe> grid = new Grid();
        filterBox.addComponents(typeSelect, filter, search);
        addComponents(filterBox, grid);
        typeSelect.addValueChangeListener(event -> {
            if (typeSelect.getValue().equals(DESCRIPTION)) {
                filterBox.removeAllComponents();
                filterBox.addComponents(typeSelect, filter, search, create);
            } else if (typeSelect.getValue().equals(PRIORITY)) {
                filterBox.removeAllComponents();
                priorityComboBox.setItems(Priority.values());
                filterBox.addComponents(typeSelect, priorityComboBox, search, create);
            } else if (typeSelect.getValue().equals(PATIENT)) {
                filterBox.removeAllComponents();
                patientComboBox.setItems(patientService.getAll());
                filterBox.addComponents(typeSelect, patientComboBox, search, create);
            } else if (typeSelect.getValue().equals(DOCTOR)) {
                filterBox.removeAllComponents();
                doctorComboBox.setItems(doctorService.getAll());
                filterBox.addComponents(typeSelect, doctorComboBox, search, create);
            } else {
                filterBox.removeAllComponents();
                filterBox.addComponents(typeSelect, search,create);
            }
        });
        search.addClickListener(e -> {
            if (checkEmptyFilter(typeSelect, filter, patientComboBox, doctorComboBox, priorityComboBox)) {
                if (typeSelect.getValue().equals(DESCRIPTION)) {
                    grid.setItems(recipeService.findByDescription(filter.getValue()));
                } else if (typeSelect.getValue().equals(PRIORITY)) {
                    grid.setItems(recipeService.findByPriority(priorityComboBox.getValue()));
                } else if (typeSelect.getValue().equals(PATIENT)) {
                    grid.setItems(recipeService.findByPatient(patientComboBox.getValue()));
                } else if (typeSelect.getValue().equals(DOCTOR)) {
                    grid.setItems(recipeService.findByDoctor(doctorComboBox.getValue()));
                } else {
                    grid.setItems(recipeService.getAll());
                }
            }
        });
        grid.setWidth("90%");
        grid.setItems(recipeService.getAll());
        grid.addColumn(Recipe::getDescription).setCaption(DESCRIPTION);
        grid.addColumn(Recipe::getPatient).setCaption(PATIENT);
        grid.addColumn(Recipe::getDoctor).setCaption(DOCTOR);
        grid.addColumn(Recipe::getCreateDate).setCaption("Create Date");
        grid.addColumn(Recipe::getValidate).setCaption("shelf life");
        grid.addColumn(Recipe::getPriority).setCaption(PRIORITY);
        grid.addColumn(recipe -> "edit",
                new ButtonRenderer(clickEvent -> {
                    Window editWindow = null;
                    editWindow = new EditWindow((Recipe) clickEvent.getItem());
                    editWindow.addCloseListener(closeEvent -> grid.setItems(recipeService.getAll()));
                    UI.getCurrent().addWindow(editWindow);
                }));

        grid.addColumn(recipe -> "remove",
                new ButtonRenderer(clickEvent -> {
                    setIcon(VaadinIcons.MINUS_CIRCLE);
                    recipeService.remove((Recipe) clickEvent.getItem());
                    grid.setItems(recipeService.getAll());
                }));
        create.addClickListener(e -> {
            Window addWindow = new AddWindow();
            addWindow.setModal(true);
            addWindow.addCloseListener(g -> {
                grid.setItems(recipeService.getAll());
            });
            UI.getCurrent().addWindow(addWindow);
        });
        filterBox.addComponent(create);
    }


    private static boolean checkEmptyFilter(ComboBox<String> typeSelect,
                                            TextField filterText,
                                            ComboBox<Patient> patientComboBox,
                                            ComboBox<Doctor> doctorComboBox,
                                            ComboBox<Priority> priorityComboBox) {
        boolean isValid = true;
        if (typeSelect.getValue().equals(DESCRIPTION)) {
            if (filterText.getValue() == null || filterText.getValue().isEmpty()) {
                filterText.setStyleName("error");
                isValid = false;
            }
        } else if (typeSelect.getValue().equals(PRIORITY)) {
            if (priorityComboBox.getValue() == null) {
                priorityComboBox.setStyleName("error");
                isValid = false;
            }
        } else if (typeSelect.getValue().equals(PATIENT)) {
            if (patientComboBox.getValue() == null) {
                patientComboBox.setStyleName("error");
                isValid = false;
            }
        } else if (typeSelect.getValue().equals(DOCTOR)) {
            if (patientComboBox.getValue() == null) {
                patientComboBox.setStyleName("error");
                isValid = false;
            }
        }
        return isValid;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}

class AddWindow extends Window {

    private static TextField description;

    private static ComboBox<Patient> patientComboBox;

    private static ComboBox<Doctor> doctorComboBox;

    private static DateField endOfShelfLife;

    private static ComboBox<Priority> priorityComboBox;

    public AddWindow() {
        super("New recipe");
        setModal(true);
        setResizable(false);
        setClosable(false);
        setHeight(50, Unit.PERCENTAGE);
        setWidth(25, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        priorityComboBox = new ComboBox<>("Priority");
        priorityComboBox.setItems(Priority.values());
        priorityComboBox.setWidth("90%");
        priorityComboBox.setEmptySelectionAllowed(false);
        priorityComboBox.setTextInputAllowed(false);
        priorityComboBox.addValueChangeListener(event -> priorityComboBox.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS));

        patientComboBox = new ComboBox<>("Patient");
        PatientService patientService = new PatientService();
        patientComboBox.setItems(patientService.getAll());
        patientComboBox.setWidth("90%");
        patientComboBox.setEmptySelectionAllowed(false);
        patientComboBox.setTextInputAllowed(false);
        patientComboBox.addValueChangeListener(event -> patientComboBox.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS));

        doctorComboBox = new ComboBox<>("Doctor");
        DoctorService doctorService = new DoctorService();
        doctorComboBox.setItems(doctorService.getAll());
        doctorComboBox.setWidth("90%");
        doctorComboBox.setEmptySelectionAllowed(false);
        doctorComboBox.setTextInputAllowed(false);
        doctorComboBox.addValueChangeListener(event -> doctorComboBox.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS));

        endOfShelfLife = new DateField("Validate");
        endOfShelfLife.setValue(LocalDate.now());
        endOfShelfLife.setWidth("90%");
        endOfShelfLife.setLocale(new Locale("ru", "RU"));
        endOfShelfLife.addValueChangeListener(event -> endOfShelfLife.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS));

        description = new TextField("Description");
        description.setWidth("90%");
        description.setMaxLength(200);
        description.addValueChangeListener(event -> description.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS));


        Button addButton = new Button("Add");
        addButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (validate()) {
                Recipe recipe = new Recipe(null, description.getValue(), patientComboBox.getValue(),
                        doctorComboBox.getValue(), LocalDate.now(), endOfShelfLife.getValue(),
                        priorityComboBox.getValue());
                RecipeService recipeService = new RecipeService();
                recipeService.add(recipe);
                close();
            }
        });

        Button cancelButton = new Button("Close");
        cancelButton.addClickListener((Button.ClickListener) clickEvent -> close());

        content.addComponentsAndExpand(description, patientComboBox, doctorComboBox, endOfShelfLife, priorityComboBox);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton, cancelButton);

        content.addComponent(buttonsLayout);
        setContent(content);
    }

    private static boolean validate() {
        boolean isValid = true;
        if (description.getValue() == null || description.getValue().isEmpty()) {
            description.setStyleName("error");
            isValid = false;
        }
        if (patientComboBox.getValue() == null) {
            patientComboBox.setStyleName("error");
            isValid = false;
        }
        if (doctorComboBox.getValue() == null) {
            doctorComboBox.setStyleName("error");
            isValid = false;
        }
        if (endOfShelfLife.getValue() == null) {
            endOfShelfLife.setStyleName("error");
            isValid = false;
        }
        if (priorityComboBox.getValue() == null) {
            priorityComboBox.setStyleName("error");
            isValid = false;
        }

        return isValid;
    }
}

class EditWindow extends Window {
    private static TextField description;

    private static ComboBox<Patient> patientComboBox;

    private static ComboBox<Doctor> doctorComboBox;

    private static DateField endOfShelfLife;

    private static ComboBox<Priority> priorityComboBox;

    public EditWindow(Recipe recipe) {
        super("Edit Window");
        setModal(true);
        setResizable(false);
        setClosable(false);
        setHeight("50%");
        setWidth("30%");


        VerticalLayout content = new VerticalLayout();
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        priorityComboBox = new ComboBox<>("Priority");
        priorityComboBox.setItems(Priority.values());
        priorityComboBox.setWidth("90%");
        priorityComboBox.setEmptySelectionAllowed(false);
        priorityComboBox.setValue(recipe.getPriority());
        patientComboBox.setTextInputAllowed(false);
        priorityComboBox.addValueChangeListener(event -> priorityComboBox.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS));

        patientComboBox = new ComboBox<>("Patient");
        PatientService patientService = new PatientService();
        patientComboBox.setItems(patientService.getAll());
        patientComboBox.setWidth("90%");
        patientComboBox.setEmptySelectionAllowed(false);
        patientComboBox.setValue(recipe.getPatient());
        patientComboBox.setTextInputAllowed(false);
        patientComboBox.addValueChangeListener(event -> patientComboBox.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS));

        doctorComboBox = new ComboBox<>("Doctor");
        DoctorService doctorService = new DoctorService();
        doctorComboBox.setItems(doctorService.getAll());
        doctorComboBox.setWidth("90%");
        doctorComboBox.setEmptySelectionAllowed(false);
        doctorComboBox.setValue(recipe.getDoctor());
        doctorComboBox.setTextInputAllowed(false);
        doctorComboBox.addValueChangeListener(event -> doctorComboBox.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS));

        endOfShelfLife = new DateField("Validate");
        endOfShelfLife.setValue(LocalDate.now());
        endOfShelfLife.setWidth("90%");
        endOfShelfLife.setLocale(new Locale("ru", "RU"));
        endOfShelfLife.setValue(recipe.getValidate());
        endOfShelfLife.addValueChangeListener(event -> endOfShelfLife.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS));

        description = new TextField("Descriprion");
        description.setWidth("90%");
        description.setMaxLength(200);
        description.setValue(recipe.getDescription());
        description.addValueChangeListener(event -> description.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS));


        Button updateButton = new Button("Update");
        updateButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (validate()) {
                Recipe nrecipe = new Recipe(recipe.getId(), description.getValue(), patientComboBox.getValue(),
                        doctorComboBox.getValue(), LocalDate.now(), endOfShelfLife.getValue(),
                        priorityComboBox.getValue());
                RecipeService recipeService = new RecipeService();
                recipeService.update(nrecipe);
                close();
            }
        });

        Button cancelButton = new Button("close");
        cancelButton.addClickListener((Button.ClickListener) clickEvent -> close());

        content.addComponentsAndExpand(description, patientComboBox, doctorComboBox, endOfShelfLife, priorityComboBox);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(updateButton, cancelButton);

        content.addComponent(buttonsLayout);
        setContent(content);
    }

    private static boolean validate() {
        boolean isValid = true;
        if (description.getValue() == null || description.getValue().isEmpty()) {
            description.setStyleName("error");
            isValid = false;
        }
        if (patientComboBox.getValue() == null) {
            patientComboBox.setStyleName("error");
            isValid = false;
        }
        if (doctorComboBox.getValue() == null) {
            doctorComboBox.setStyleName("error");
            isValid = false;
        }
        if (endOfShelfLife.getValue() == null) {
            endOfShelfLife.setStyleName("error");
            isValid = false;
        }
        if (priorityComboBox.getValue() == null) {
            priorityComboBox.setStyleName("error");
            isValid = false;
        }

        return isValid;
    }
}

