package jaep.springframwork.petclinic.bootstrap;

import jaep.springframework.model.Owner;
import jaep.springframework.services.OwnerService;
import jaep.springframework.services.VetService;
import jaep.springframework.services.map.OwnerServiceMap;
import jaep.springframework.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private  final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader() {
        this.ownerService = new OwnerServiceMap();
        this.vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("User1");
        owner1.setLastName("Doe1");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(1L);
        owner2.setFirstName("User2");
        owner2.setLastName("Doe2");

        ownerService.save(owner2);

        Owner owner3 = new Owner();
        owner3.setId(1L);
        owner3.setFirstName("User3");
        owner3.setLastName("Doe3");

        ownerService.save(owner3);

        Owner owner4 = new Owner();
        owner4.setId(1L);
        owner4.setFirstName("User4");
        owner4.setLastName("Doe4");

        ownerService.save(owner4);
    }
}
