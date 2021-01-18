package com.niveksys.petclinic.repository;

import com.niveksys.petclinic.model.PetType;

import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {

}
