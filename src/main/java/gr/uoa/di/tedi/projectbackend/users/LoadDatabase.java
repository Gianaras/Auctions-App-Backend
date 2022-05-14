package gr.uoa.di.tedi.projectbackend.users;

import gr.uoa.di.tedi.projectbackend.users.model.Items;
import gr.uoa.di.tedi.projectbackend.users.model.User;
import gr.uoa.di.tedi.projectbackend.users.repos.BidderRepository;
import gr.uoa.di.tedi.projectbackend.users.repos.ItemsRepository;
import gr.uoa.di.tedi.projectbackend.users.repos.SellerRepository;
import gr.uoa.di.tedi.projectbackend.users.repos.UserRepository;
import gr.uoa.di.tedi.projectbackend.users.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;

@Configuration
@Slf4j
class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            // if no admin exists, add one
            if (repository.getAdmin().isEmpty()) {
                log.info("Adding admin " + repository.save(new User("Gianarg", "admin123",
                        "giannis", "Argiros", "Gianarg@mail.com", "0123456789",
                        "Paradeisos 666", "Ellas", "Kapou", true, true)));
            }
            // mock users for testing (added every time backEnd runs)
            log.info("Preloading " + repository.save(new User("MichaelCaineReal", "innit123",
                    "Michael", "Caine", "MCaine@mail.com", "0123456789",
                    "InYourHouse 69", "England", "Liverpool", false, false)));

        };
    }
    @Bean
    CommandLineRunner initDatabase2(UserRepository userRepo, SellerRepository sellerRepo, BidderRepository bidderRepo){
        UserService userService =  new UserService(userRepo,sellerRepo,bidderRepo);
        return args -> {
            log.info("test" + userService.addUser(new User("sellettest", "innit123",
                    "Msdfichael", "Caiasdfne", "MCaine@mail.com", "0123456789",
                    "InYourHouse 69", "England", "Liverpool", false, false)));
        };
    }
}

