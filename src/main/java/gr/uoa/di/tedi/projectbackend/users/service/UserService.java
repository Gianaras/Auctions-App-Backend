package gr.uoa.di.tedi.projectbackend.users.service;

import gr.uoa.di.tedi.projectbackend.users.handling.UserNotFoundException;
import gr.uoa.di.tedi.projectbackend.users.model.Bidder;
import gr.uoa.di.tedi.projectbackend.users.model.Seller;
import gr.uoa.di.tedi.projectbackend.users.model.User;
import gr.uoa.di.tedi.projectbackend.users.repos.BidderRepository;
import gr.uoa.di.tedi.projectbackend.users.repos.SellerRepository;
import gr.uoa.di.tedi.projectbackend.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// the service uses the repository

@Service
public class UserService {
    private final UserRepository repository;
    private final BidderRepository bidderRepo;
    private final SellerRepository sellerRepo;

    @Autowired
    public UserService(UserRepository repository, SellerRepository sellerRepo, BidderRepository bidderRepo) {
        this.sellerRepo=sellerRepo;
        this.bidderRepo=bidderRepo;
        this.repository = repository;
    }

    public List<User> getAllUsers() { return repository.findAll(); }

    public User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getUserLike(String name) { return repository.getUsernameLike(name); }

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

    public void deleteUser(Long id) { repository.deleteById(id); }

    public User updateUser(User user) { return repository.save(user); }
}
