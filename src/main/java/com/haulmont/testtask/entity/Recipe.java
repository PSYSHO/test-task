package com.haulmont.testtask.entity;

import java.time.LocalDate;
import java.util.Date;

public class Recipe {
    private Long id;
    private String description;
    private  Patient patient;
    private  Doctor doctor;
    private LocalDate creationDate;
    private LocalDate endOfShelfLife;
    private Priority priority;

    public Recipe() {
    }

    public Recipe(Long id, String description, Patient patient, Doctor doctor, LocalDate createDate, LocalDate endOfShelfLife, Priority priority) {
        this.id = id;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.creationDate = createDate;
        this.endOfShelfLife = endOfShelfLife;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getCreateDate() {
        return creationDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.creationDate = createDate;
    }

    public LocalDate getValidate() {
        return endOfShelfLife;
    }

    public void setValidate(LocalDate endOfShelfLife) {
        this.endOfShelfLife = endOfShelfLife;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
