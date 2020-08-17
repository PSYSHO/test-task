package com.haulmont.testtask.entity;

public class Doctor {
    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String specialization;
    private Boolean select;

    public Doctor() {
    }

    public Doctor(Long id, String firstName, String secondName, String lastName, String specialization) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.select = select;
    }

    public Doctor(String firstName, String secondName, String lastName, String specialization) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    @Override
    public String toString() {
        return secondName + " " + firstName + " " + specialization;
    }
}
