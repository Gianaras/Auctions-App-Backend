package gr.uoa.di.tedi.projectbackend;

import gr.uoa.di.tedi.projectbackend.model.Item;
import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.model.User;
import gr.uoa.di.tedi.projectbackend.repos.*;
import gr.uoa.di.tedi.projectbackend.service.ItemsService;
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
    CommandLineRunner initDatabase3(ItemsRepository itemsRepository, ItemRepository itemRepository,
                                    BCryptPasswordEncoder bCryptPasswordEncoder) {
        long now = System.currentTimeMillis();
        ItemsService itemsService = new ItemsService(itemsRepository, itemRepository);

        Items listing =  new Items(10.0, 0.0,
                1.0, new Timestamp(now), new Timestamp(now + 600000), 0);
        itemsService.addItems(listing);

        Item iceCream = new Item("ice cream", "chocolate",listing);
        itemRepository.save(iceCream);


        listing.getItems().add(iceCream);
        itemsService.updateItem(listing);

        return args -> {
            log.info("Adding items " + listing);
        };
    }
}

