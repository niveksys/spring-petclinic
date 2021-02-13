package com.niveksys.petclinic.repository;

import java.util.List;

import com.niveksys.petclinic.model.Owner;

import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);

    List<Owner> findByLastNameContaining(String lastName);
}
