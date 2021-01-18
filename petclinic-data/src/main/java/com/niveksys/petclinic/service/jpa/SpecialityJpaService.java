package com.niveksys.petclinic.service.jpa;

import java.util.HashSet;
import java.util.Set;

import com.niveksys.petclinic.model.Speciality;
import com.niveksys.petclinic.repository.SpecialityRepository;
import com.niveksys.petclinic.service.SpecialityService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("jpa")
public class SpecialityJpaService implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialityJpaService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        Set<Speciality> specialities = new HashSet<>();
        this.specialityRepository.findAll().forEach(specialities::add);
        return specialities;
    }

    @Override
    public Speciality findById(Long id) {
        return this.specialityRepository.findById(id).orElse(null);
    }

    @Override
    public Speciality save(Speciality object) {
        return this.specialityRepository.save(object);
    }

    @Override
    public void delete(Speciality object) {
        this.specialityRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        this.specialityRepository.deleteById(id);
    }

}
