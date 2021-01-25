package com.niveksys.petclinic.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import com.niveksys.petclinic.model.Owner;
import com.niveksys.petclinic.service.OwnerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = OwnerController.class)
public class OwnerControllerTests {

    @MockBean
    OwnerService ownerService;

    @Autowired
    MockMvc mockMvc;

    Set<Owner> owners;

    @BeforeEach
    public void setUp() {
        this.owners = new HashSet<>();
        this.owners.add(Owner.builder().id(1L).build());
        this.owners.add(Owner.builder().id(2L).build());
    }

    @Test
    public void listOwners() throws Exception {
        when(this.ownerService.findAll()).thenReturn(this.owners);

        this.mockMvc.perform(get("/owners")).andExpect(status().isOk()).andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    public void listOwnersByIndex() throws Exception {
        when(this.ownerService.findAll()).thenReturn(this.owners);

        this.mockMvc.perform(get("/owners/index")).andExpect(status().isOk()).andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    public void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(view().name("notimplemented"));

        verifyNoMoreInteractions(this.ownerService);
    }
}
