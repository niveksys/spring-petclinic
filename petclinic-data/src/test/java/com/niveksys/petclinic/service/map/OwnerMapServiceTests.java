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

    private static final String LAST_NAME = "Brown";

    OwnerMapService service;

    Owner owner;

    @BeforeEach
    public void setUp() {
        this.service = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        Owner newOwner = Owner.builder().lastName(LAST_NAME).build();
        this.owner = this.service.save(newOwner);
    }

    @Test
    public void findAll() {
        Set<Owner> returnOwnerSet = this.service.findAll();

        assertEquals(1, returnOwnerSet.size());
    }

    @Test
    public void findById() {
        Owner returnOwner = this.service.findById(this.owner.getId());

        assertEquals(this.owner.getId(), returnOwner.getId());
    }

    @Test
    public void save() {
        Owner newOwner = Owner.builder().build();
        Owner savedOwner = this.service.save(newOwner);

        assertNotEquals(this.owner.getId(), savedOwner.getId());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    public void delete() {
        this.service.delete(this.owner);

        assertEquals(0, this.service.findAll().size());
    }

    @Test
    public void deleteById() {
        this.service.deleteById(this.owner.getId());

        assertEquals(0, this.service.findAll().size());
    }

    @Test
    public void findByLastName() {
        Owner returnOwner = this.service.findByLastName(LAST_NAME);

        assertNotNull(returnOwner);
        assertEquals(LAST_NAME, returnOwner.getLastName());
    }

    @Test
    public void findByLastNameNotFound() {
        Owner returnOwner = this.service.findByLastName("foo");

        assertNull(returnOwner);
    }
}
