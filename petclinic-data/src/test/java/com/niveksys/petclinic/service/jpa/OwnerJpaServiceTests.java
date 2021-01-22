package com.niveksys.petclinic.service.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.niveksys.petclinic.model.Owner;
import com.niveksys.petclinic.repository.OwnerRepository;
import com.niveksys.petclinic.repository.PetRepository;
import com.niveksys.petclinic.repository.PetTypeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OwnerJpaServiceTests {

    private static final String LAST_NAME = "Brown";

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerJpaService service;

    Owner owner;

    @BeforeEach
    public void setUp() {
        owner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    public void findAll() {
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(Owner.builder().id(1L).build());
        ownerSet.add(Owner.builder().id(2L).build());

        when(this.ownerRepository.findAll()).thenReturn(ownerSet);

        Set<Owner> returnOwnerSet = this.service.findAll();

        assertNotNull(returnOwnerSet);
        assertEquals(ownerSet.size(), returnOwnerSet.size());
    }

    @Test
    public void findById() {
        when(this.ownerRepository.findById(anyLong())).thenReturn(Optional.of(this.owner));

        Owner returnOwner = this.service.findById(this.owner.getId());

        assertNotNull(returnOwner);
    }

    @Test
    public void save() {
        Owner ownerBuild = Owner.builder().id(1L).build();

        when(this.ownerRepository.save(any())).thenReturn(this.owner);

        Owner savedOwner = this.service.save(ownerBuild);

        assertNotNull(savedOwner);
        verify(this.ownerRepository).save(any());
    }

    @Test
    public void delete() {
        this.service.delete(this.owner);

        verify(this.ownerRepository).delete(any());
    }

    @Test
    public void deleteById() {
        this.service.deleteById(this.owner.getId());

        verify(this.ownerRepository).deleteById(anyLong());
    }

    @Test
    public void findByLastName() {
        when(this.ownerRepository.findByLastName(any())).thenReturn(this.owner);

        Owner returnOwner = this.service.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, returnOwner.getLastName());
        verify(this.ownerRepository).findByLastName(any());
    }
}
