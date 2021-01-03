package com.niveksys.petclinic.service;

import java.util.Set;

import com.niveksys.petclinic.model.Owner;

public interface OwnerService {

    Owner findByLastName(String lastName);

    Owner findById(Long id);

    Owner save(Owner owner);

    Set<Owner> findAll();

}
