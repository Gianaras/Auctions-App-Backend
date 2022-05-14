package gr.uoa.di.tedi.projectbackend.service;

import gr.uoa.di.tedi.projectbackend.handling.UserNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.User;
import gr.uoa.di.tedi.projectbackend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// the service uses the repository

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) { this.repository = repository; }

    public List<User> getAllUsers() { return repository.findAll(); }

    public User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getUserLike(String name) { return repository.getUsernameLike(name); }

    public User addUser(User newUser) { return repository.save(newUser); }

    public void deleteUser(Long id) { repository.deleteById(id); }

    public User updateUser(User user) { return repository.save(user); }
}
