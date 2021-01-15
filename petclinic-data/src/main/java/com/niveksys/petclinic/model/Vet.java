package com.niveksys.petclinic.model;

import java.util.HashSet;
import java.util.Set;

public class Vet extends Person {

    private static final long serialVersionUID = 1L;

    private Set<Speciality> specialties = new HashSet<>();

    public Set<Speciality> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Speciality> specialties) {
        this.specialties = specialties;
    }
}
