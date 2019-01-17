package jaep.springframework.petclinic.bootstrap;

import jaep.springframework.petclinic.model.Owner;
import jaep.springframework.petclinic.model.Vet;
import jaep.springframework.petclinic.services.OwnerService;
import jaep.springframework.petclinic.services.VetService;
import jaep.springframework.petclinic.services.map.OwnerServiceMap;
import jaep.springframework.petclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private  final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("User1");
        owner1.setLastName("Doe1");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("User2");
        owner2.setLastName("Doe2");

        ownerService.save(owner2);

        Owner owner3 = new Owner();
        owner3.setId(3L);
        owner3.setFirstName("User3");
        owner3.setLastName("Doe3");

        ownerService.save(owner3);

        Owner owner4 = new Owner();
        owner4.setId(4L);
        owner4.setFirstName("User4");
        owner4.setLastName("Doe4");

        ownerService.save(owner4);

        //---------------------------------------------------

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Juan");
        vet1.setLastName("Perez");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("John");
        vet2.setLastName("Doe");

        vetService.save(vet2);

        Vet vet3 = new Vet();
        vet3.setId(3L);
        vet3.setFirstName("Jim");
        vet3.setLastName("Cox");

        vetService.save(vet3);
    }
}
