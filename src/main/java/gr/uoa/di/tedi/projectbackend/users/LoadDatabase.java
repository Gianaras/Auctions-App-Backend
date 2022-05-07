package gr.uoa.di.tedi.projectbackend.users;

import gr.uoa.di.tedi.projectbackend.users.model.User;
import gr.uoa.di.tedi.projectbackend.users.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new User("MichaelCaineReal", "innit",
                    "Michael", "Caine", "MCaine@mail.com", "0123456789",
                    "InYourHouse 69", "England", "Liverpool", false)));

            log.info("Preloading " + repository.save(new User("slipperyNip420", "123",
                    "Jonathan", "Bayblade", "Jblade@inlook.com", "6969696969",
                    "21 Jump Street", "USA", "Chicago", false)));
        };
    }
}

