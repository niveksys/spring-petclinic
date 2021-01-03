package com.niveksys.petclinic.service;

import com.niveksys.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

}
