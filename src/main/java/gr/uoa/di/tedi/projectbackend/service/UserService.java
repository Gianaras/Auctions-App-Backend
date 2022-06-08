package gr.uoa.di.tedi.projectbackend.service;

import gr.uoa.di.tedi.projectbackend.handling.UserNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.Bidder;
import gr.uoa.di.tedi.projectbackend.model.Seller;
import gr.uoa.di.tedi.projectbackend.model.User;
import gr.uoa.di.tedi.projectbackend.repos.BidderRepository;
import gr.uoa.di.tedi.projectbackend.repos.SellerRepository;
import gr.uoa.di.tedi.projectbackend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// the service uses the repository

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final BidderRepository bidderRepo;
    private final SellerRepository sellerRepo;

    @Autowired
    public UserService(UserRepository repository, SellerRepository sellerRepo, BidderRepository bidderRepo) {
        this.sellerRepo = sellerRepo;
        this.bidderRepo = bidderRepo;
        this.repository = repository;
    }

    public List<User> getAllUsers() { return repository.findAll(); }

    public User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getUserLike(String name) { return repository.getUsernameLike(name); }

    public void deleteUser(Long id) { repository.deleteById(id); }

    public User updateUser(User user) { return repository.save(user); }

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
