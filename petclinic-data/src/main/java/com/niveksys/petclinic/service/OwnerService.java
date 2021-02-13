package com.niveksys.petclinic.service;

import java.util.List;

import com.niveksys.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

    List<Owner> findByLastNameContaining(String lastName);
}
