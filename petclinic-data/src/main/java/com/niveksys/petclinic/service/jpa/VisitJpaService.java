package com.niveksys.petclinic.service.jpa;

import java.util.HashSet;
import java.util.Set;

import com.niveksys.petclinic.model.Visit;
import com.niveksys.petclinic.repository.VisitRepository;
import com.niveksys.petclinic.service.VisitService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("jpa")
public class VisitJpaService implements VisitService {

    private final VisitRepository visitRepository;

    public VisitJpaService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Set<Visit> findAll() {
        Set<Visit> visits = new HashSet<>();
        this.visitRepository.findAll().forEach(visits::add);
        return visits;
    }

    @Override
    public Visit findById(Long id) {
        return this.visitRepository.findById(id).orElse(null);
    }

    @Override
    public Visit save(Visit object) {
        return this.visitRepository.save(object);
    }

    @Override
    public void delete(Visit object) {
        this.visitRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        this.visitRepository.deleteById(id);
    }

}
