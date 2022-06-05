package gr.uoa.di.tedi.projectbackend;

import gr.uoa.di.tedi.projectbackend.model.Item;
import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.model.User;
import gr.uoa.di.tedi.projectbackend.repos.BidderRepository;
import gr.uoa.di.tedi.projectbackend.repos.ItemsRepository;
import gr.uoa.di.tedi.projectbackend.repos.SellerRepository;
import gr.uoa.di.tedi.projectbackend.repos.UserRepository;
import gr.uoa.di.tedi.projectbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return args -> {
            // if no admin exists, add one
            if (repository.getAdmin().isEmpty()) {
                log.info("Adding admin " + repository.save(new User("Gianarg",
                        bCryptPasswordEncoder.encode("admin123"), // passwords must be encrypted in db
                        "giannis", "Argiros", "Gianarg@mail.com", "0123456789",
                        "Paradeisos 666", "Ellas", "Kapou", true, true)));
            }
            // mock users for testing (added every time backEnd runs)
            log.info("Preloading " + repository.save(new User("MichaelCaineReal",
                    bCryptPasswordEncoder.encode("innit123"),
                    "Michael", "Caine", "MCaine@mail.com", "0123456789",
                    "InYourHouse 69", "England", "Liverpool", false, false)));

        };
    }

    @Bean
    CommandLineRunner initDatabase2(UserRepository userRepo, SellerRepository sellerRepo, BidderRepository bidderRepo,
                                    BCryptPasswordEncoder bCryptPasswordEncoder) {
        UserService userService =  new UserService(userRepo, sellerRepo, bidderRepo);
        return args -> log.info("test" + userService.addUser(new User("sellettest",
                bCryptPasswordEncoder.encode("innit123"),
                "Msdfichael", "Caiasdfne", "MCaine@mail.com", "0123456789",
                "InYourHouse 69", "England", "Liverpool", false, false)));
    }

    @Bean
    CommandLineRunner initDatabase3(ItemsRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        long now = System.currentTimeMillis();

        Set<Item> iceCreamSet = new HashSet<>();
        Item iceCream = new Item();
        iceCream.setName("ice cream");
        iceCream.setDescription("chocolate");
        iceCreamSet.add(iceCream);

        Set<Item> vampireSet = new HashSet<>();

        Item jarOfBlood = new Item();
        jarOfBlood.setName("jar of human blood");
        jarOfBlood.setDescription("extracted during full moon.");
        vampireSet.add(jarOfBlood);

        Item cape = new Item();
        cape.setName("cool cape");
        cape.setDescription("quality fabric.");
        vampireSet.add(cape);

        return args -> {
            log.info("Adding items " + repository.save(new Items(iceCreamSet, 10.0, 0.0, 1.0,
                    new Timestamp(now), new Timestamp(now + 600000), 0)));
            log.info("Adding items " + repository.save(new Items(vampireSet, 5000.0, 0.0,
                    100.0, new Timestamp(now), new Timestamp(now + 600000), 0)));
        };
    }
}

