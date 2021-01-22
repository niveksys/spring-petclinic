package com.niveksys.petclinic.service.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import com.niveksys.petclinic.model.Owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OwnerMapServiceTests {

    OwnerMapService ownerMapService;
    Long ownerId;
    String lastName;

    @BeforeEach
    public void setUp() {
        this.ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        Owner owner = Owner.builder().lastName("Brown").build();
        this.ownerMapService.save(owner);
        this.ownerId = owner.getId();
        this.lastName = owner.getLastName();
    }

    @Test
    public void findAll() {
        Set<Owner> ownerSet = this.ownerMapService.findAll();

        assertEquals(1, ownerSet.size());
    }

    @Test
    public void findById() {
        Owner owner = this.ownerMapService.findById(this.ownerId);

        assertEquals(this.ownerId, owner.getId());
    }

    @Test
    public void save() {
        Owner owner = Owner.builder().build();
        Owner savedOwner = this.ownerMapService.save(owner);

        assertNotEquals(this.ownerId, savedOwner.getId());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    public void delete() {
        this.ownerMapService.delete(this.ownerMapService.findById(this.ownerId));

        assertEquals(0, this.ownerMapService.findAll().size());
    }

    @Test
    public void deleteById() {
        this.ownerMapService.deleteById(this.ownerId);

        assertEquals(0, this.ownerMapService.findAll().size());
    }

    @Test
    public void findByLastName() {
        Owner owner = this.ownerMapService.findByLastName(this.lastName);

        assertNotNull(owner);
        assertEquals(this.lastName, owner.getLastName());
    }

    @Test
    public void findByLastNameNotFound() {
        Owner owner = this.ownerMapService.findByLastName("foo");

        assertNull(owner);
    }
}
