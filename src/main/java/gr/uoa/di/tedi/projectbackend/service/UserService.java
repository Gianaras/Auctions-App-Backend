package gr.uoa.di.tedi.projectbackend.service;

import gr.uoa.di.tedi.projectbackend.handling.UserNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.User;
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
