package gr.uoa.di.tedi.projectbackend;

import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.model.User;
import gr.uoa.di.tedi.projectbackend.repos.ItemsRepository;
import gr.uoa.di.tedi.projectbackend.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;

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

            log.info("Preloading " + repository.save(new User("slipperyNip420",
                    bCryptPasswordEncoder.encode("123123"),
                    "Jonathan", "Bayblade", "Jblade@inlook.com", "6969696969",
                    "21 Jump Street", "USA", "Chicago", false, false)));
        };
    }
    @Bean
    CommandLineRunner initDatabase2(UserRepository userRepo,ItemsRepository itemRepo){
        return args -> {
            log.info("Preloading: "+ itemRepo.save( new Items("test",100,50,40,"This is a description",
                    new Timestamp(1),new Timestamp(10),5)));
        };
    }
}

