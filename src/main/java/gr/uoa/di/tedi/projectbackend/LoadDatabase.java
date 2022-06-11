package gr.uoa.di.tedi.projectbackend;

import gr.uoa.di.tedi.projectbackend.model.*;
import gr.uoa.di.tedi.projectbackend.repos.*;
import gr.uoa.di.tedi.projectbackend.service.ItemsService;
import gr.uoa.di.tedi.projectbackend.service.MessageService;
import gr.uoa.di.tedi.projectbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(ItemsRepository itemsRepository, ItemRepository itemRepository,
                                   MessageRepository messageRepository, CategoryRepository categoryRepository,
                                   BidRepository bidRepository, UserRepository userRepository,ImageRepository imageRepository,
                                   LocationRepository locationRepository, SellerRepository sellerRepository,
                                   BidderRepository bidderRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        long now = System.currentTimeMillis();

        UserService userService =  new UserService(userRepository, sellerRepository, bidderRepository);
        ItemsService itemsService = new ItemsService(itemsRepository, itemRepository, bidRepository,
                userRepository, categoryRepository, locationRepository,imageRepository);

        // if no admin exists, add one
        if (userRepository.getAdmin().isEmpty()) {
            userService.addUser(new User("Gianarg",
                    bCryptPasswordEncoder.encode("admin123"), // passwords must be encrypted in db
                    "giannis", "Argiros", "Gianarg@mail.com", "0123456789",
                    "Paradeisos 666", "Ellas", "Kapou", true, true));
        }

        // mock users for testing (added every time backEnd runs)
        userService.addUser(new User("MichaelCaineReal",
                bCryptPasswordEncoder.encode("innit123"),
                "Michael", "Caine", "MCaine@mail.com", "0123456789",
                "InYourHouse 69", "England", "Liverpool", false, false));

        userService.addUser(new User("MichaelCaineReal2",
                bCryptPasswordEncoder.encode("innit123"),
                "Michael", "Caine", "MCadine@mail.com", "0123456789",
                "InYourHouse 69", "England", "Liverpool", false, false));

        // add locations
        Location location = new Location("United Kingdom", "London", "-0.118092", "51.509865",
                userRepository.findByUsername("MichaelCaineReal"));

        Location location2 = new Location("Greece", "Athens", "23.727539", "37.983810",
                userRepository.findByUsername("Gianarg"));

        // add categories if they don't exist
        Category foodCategory = new Category("food");
        Category toysCategory = new Category("toys");
        Category electronicsCategory = new Category("electronics");
        Category booksCategory = new Category("books");
        Category moviesCategory = new Category("movies");
        Category videoGamesCategory = new Category("video games");
        Category clothingCategory = new Category("clothing");
        Category footwearCategory = new Category("footwear");
        Category furnitureCategory = new Category("furniture");
        Category musicCategory = new Category("music");

        if (categoryRepository.count() == 0) {
            categoryRepository.save(foodCategory);
            categoryRepository.save(toysCategory);
            categoryRepository.save(electronicsCategory);
            categoryRepository.save(booksCategory);
            categoryRepository.save(moviesCategory);
            categoryRepository.save(videoGamesCategory);
            categoryRepository.save(clothingCategory);
            categoryRepository.save(footwearCategory);
            categoryRepository.save(furnitureCategory);
            categoryRepository.save(musicCategory);
        }

        // add item
        Items listing = new Items(10.0, 0.0, 1.0, new Timestamp(now),
                new Timestamp(now + 999999999), 0,
                userRepository.findByUsername("MichaelCaineReal").getSeller(),
                location, new HashSet<>(Arrays.asList(foodCategory)));
        itemsService.addItems(listing);

        Item iceCream = new Item("ice cream", "chocolate", listing);
        itemRepository.save(iceCream);

        Item Blood = new Item("jar of human blood", "Extracted during full moon", listing);
        itemRepository.save(Blood);

        itemsService.addNewItem(listing, iceCream);
        itemsService.addNewItem(listing, Blood);

        // add second item
        Items listing2 = new Items(700, 0.0, 350, new Timestamp(now),
                new Timestamp(now + 999999999), 0,
                userRepository.findByUsername("MichaelCaineReal2").getSeller(),
                location2, new HashSet<>(Arrays.asList(videoGamesCategory, electronicsCategory)));
        itemsService.addItems(listing2);

        Item playstation = new Item("PlayStation 5", "Play has no limits", listing2);
        itemRepository.save(playstation);

        itemsService.addNewItem(listing2, playstation);

        // messages
        MessageService messageService =  new MessageService(messageRepository,userRepository);
        messageService.addMessage("Gianarg","MichaelCaineReal","Hello!");
        messageService.addMessage("MichaelCaineReal","Gianarg","Wassup my g.");


        return args -> log.info("Preloading testing data");
    }
}

