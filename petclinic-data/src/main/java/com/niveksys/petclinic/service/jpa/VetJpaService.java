package com.niveksys.petclinic.service.jpa;

import java.util.HashSet;
import java.util.Set;

import com.niveksys.petclinic.model.Vet;
import com.niveksys.petclinic.repository.VetRepostiory;
import com.niveksys.petclinic.service.VetService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("jpa")
public class VetJpaService implements VetService {

    private final VetRepostiory vetRepository;

    public VetJpaService(VetRepostiory vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Set<Vet> findAll() {
        Set<Vet> vets = new HashSet<>();
        this.vetRepository.findAll().forEach(vets::add);
        return vets;
    }

    @Override
    public Vet findById(Long id) {
        return this.vetRepository.findById(id).orElse(null);
    }

    @Override
    public Vet save(Vet object) {
        return this.vetRepository.save(object);
    }

    @Override
    public void delete(Vet object) {
        this.vetRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        this.vetRepository.deleteById(id);
    }

}
