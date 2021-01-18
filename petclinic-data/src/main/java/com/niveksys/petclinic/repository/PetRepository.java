package com.niveksys.petclinic.repository;

import com.niveksys.petclinic.model.Pet;

import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {

}
