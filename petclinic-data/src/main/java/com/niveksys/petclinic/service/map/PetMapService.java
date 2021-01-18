package com.niveksys.petclinic.service.map;

import java.util.Set;

import com.niveksys.petclinic.model.Pet;
import com.niveksys.petclinic.service.PetService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({ "default", "map" })
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {

    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Pet save(Pet object) {
        return super.save(object);
    }

    @Override
    public void delete(Pet object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
