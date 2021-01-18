package com.niveksys.petclinic.bootstrap;

import java.time.LocalDate;

import com.niveksys.petclinic.model.Owner;
import com.niveksys.petclinic.model.Pet;
import com.niveksys.petclinic.model.PetType;
import com.niveksys.petclinic.model.Speciality;
import com.niveksys.petclinic.model.Vet;
import com.niveksys.petclinic.model.Visit;
import com.niveksys.petclinic.service.OwnerService;
import com.niveksys.petclinic.service.PetTypeService;
import com.niveksys.petclinic.service.SpecialityService;
import com.niveksys.petclinic.service.VetService;
import com.niveksys.petclinic.service.VisitService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
            SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = this.petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = this.petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("radiology");
        Speciality savedRadiology = this.specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("surgery");
        Speciality savedSurgery = this.specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentistry");
        Speciality savedDentistry = this.specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Waston");
        owner1.setAddress("123 Brickerel");
        owner1.setCity("Miami");
        owner1.setTelephone("1234567890");

        Pet mikesPet = new Pet();
        mikesPet.setName("Rosco");
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setOwner(owner1);
        mikesPet.setBirthday(LocalDate.now());
        owner1.getPets().add(mikesPet);

        this.ownerService.save(owner1);

        Owner owner2 = new Owner();

        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("123 Brickerel");
        owner2.setCity("Miami");
        owner2.setTelephone("1234567890");

        Pet fionaPet = new Pet();
        fionaPet.setName("Kitty");
        fionaPet.setPetType(savedCatPetType);
        fionaPet.setOwner(owner2);
        fionaPet.setBirthday(LocalDate.now());
        owner2.getPets().add(fionaPet);

        this.ownerService.save(owner2);

        System.out.println("Loaded Owners....");

        Visit catVisit = new Visit();
        catVisit.setPet(fionaPet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");

        this.visitService.save(catVisit);

        System.out.println("Loaded Visits....");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialties().add(savedRadiology);

        this.vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialties().add(savedSurgery);

        this.vetService.save(vet2);

        System.out.println("Loaded Vets....");
    }

}
