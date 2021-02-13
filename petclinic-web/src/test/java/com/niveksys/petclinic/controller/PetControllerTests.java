package com.niveksys.petclinic.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import com.niveksys.petclinic.model.Owner;
import com.niveksys.petclinic.model.Pet;
import com.niveksys.petclinic.model.PetType;
import com.niveksys.petclinic.service.OwnerService;
import com.niveksys.petclinic.service.PetService;
import com.niveksys.petclinic.service.PetTypeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PetController.class)
public class PetControllerTests {

    @MockBean
    PetService petService;

    @MockBean
    OwnerService ownerService;

    @MockBean
    PetTypeService petTypeService;

    @Autowired
    MockMvc mockMvc;

    Owner owner;
    Set<PetType> petTypes;

    @BeforeEach
    public void setUp() {
        this.owner = Owner.builder().id(1L).build();

        this.petTypes = new HashSet<>();
        this.petTypes.add(PetType.builder().id(1L).name("Dog").build());
        this.petTypes.add(PetType.builder().id(2L).name("Cat").build());
    }

    @Test
    public void newPet() throws Exception {
        // given
        when(this.ownerService.findById(anyLong())).thenReturn(this.owner);
        when(this.petTypeService.findAll()).thenReturn(this.petTypes);

        // when
        mockMvc.perform(get("/owners/1/pets/new")).andExpect(status().isOk())
                .andExpect(model().attributeExists("owner")).andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/edit"));
    }

    @Test
    public void create() throws Exception {
        // given
        when(this.petTypeService.findAll()).thenReturn(this.petTypes);
        when(this.ownerService.findById(anyLong())).thenReturn(this.owner);
        when(this.ownerService.save(any())).thenReturn(this.owner);

        // when
        mockMvc.perform(post("/owners/1/pets")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        // then
        verify(this.ownerService).save(any());
    }

    @Test
    public void edit() throws Exception {
        // given
        when(this.petTypeService.findAll()).thenReturn(this.petTypes);
        when(this.ownerService.findById(anyLong())).thenReturn(this.owner);
        when(this.petService.findById(anyLong()))
                .thenReturn(Pet.builder().id(2L).owner(Owner.builder().id(1L).build()).build());

        // when
        mockMvc.perform(get("/owners/1/pets/2/edit")).andExpect(status().isOk())
                .andExpect(model().attributeExists("owner")).andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/edit"));
    }

    @Test
    public void update() throws Exception {
        // given
        when(this.ownerService.findById(anyLong())).thenReturn(this.owner);
        when(this.petTypeService.findAll()).thenReturn(this.petTypes);
        when(this.petService.save(any()))
                .thenReturn(Pet.builder().id(2L).owner(Owner.builder().id(1L).build()).build());

        // when
        mockMvc.perform(post("/owners/1/pets/2")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        // then
        verify(this.petService).save(any());
    }
}
