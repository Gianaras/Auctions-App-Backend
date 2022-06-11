package gr.uoa.di.tedi.projectbackend.service;

import gr.uoa.di.tedi.projectbackend.handling.BidderNotFoundException;
import gr.uoa.di.tedi.projectbackend.handling.CategoryNotFoundException;
import gr.uoa.di.tedi.projectbackend.handling.SellerNotFoundException;
import gr.uoa.di.tedi.projectbackend.handling.UserNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.*;
import gr.uoa.di.tedi.projectbackend.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// the service uses the repository

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final BidderRepository bidderRepo;
    private final SellerRepository sellerRepo;
    private final CategoryRepository categoryRepo;
    private final MessageRepository messageRepo;

    @Autowired
    public UserService(UserRepository repository, SellerRepository sellerRepo, BidderRepository bidderRepo,
                       CategoryRepository categoryRepo, MessageRepository messageRepo) {
        this.sellerRepo = sellerRepo;
        this.bidderRepo = bidderRepo;
        this.repository = repository;
        this.categoryRepo = categoryRepo;
        this.messageRepo = messageRepo;
    }

    public List<User> getAllUsers() { return repository.findAll(); }

    public User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getUserLike(String name) { return repository.getUsernameLike(name); }

    public void deleteUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // delete items of user
        Set<Items> allItems = user.getSeller().getItems();
        for (Items items : allItems) {
            for (Category category : items.getCategories()) {
                category.getItems().remove(items);
                categoryRepo.save(category);
            }
        }

        // delete messages of user
        List<Message> messages = messageRepo.getUserRelated(user.getUsername());
        messageRepo.deleteAll(messages);

        // delete seller of user (also deletes bidder and user)
        sellerRepo.deleteById(user.getSeller().getId());
    }

    // only activates user
    public User updateUser(User user) {
        User realUser = repository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));
        realUser.setActivated(true);
        repository.save(realUser);
        return realUser;
    }

    public User addUser(User newUser) {
        //Bidder and seller relations are created whenever a new user is created

        //Because user must exist before we create bidder,seller
        //we save the user here
        repository.save(newUser);
        //add bidders and sellers
        newUser.setBidder(bidderRepo.save(new Bidder(newUser,1000)));
        newUser.setSeller(sellerRepo.save(new Seller(newUser,1000)));
        //and then we update the user
        return repository.save(newUser);
    }

    // returns actual user object
    public User getUserFromUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        return user;
    }

    // check if username exists
    public Boolean usernameExists(String username) {
        User user = repository.findByUsername(username);
        return user != null;
    }

    @Override // override from UserDetailsService. Returns UserDetails object which includes user roles
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        gr.uoa.di.tedi.projectbackend.model.User applicationUser = repository.findByUsername(username);
        if (applicationUser == null)
            throw new UsernameNotFoundException(username);

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        String role = "user";
        if (applicationUser.isAdmin()) role = "admin";

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return new org.springframework.security.core.userdetails.User(applicationUser.getUsername(),
                applicationUser.getPassword(), authorities);
    }
}
