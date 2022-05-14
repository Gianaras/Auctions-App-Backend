package gr.uoa.di.tedi.projectbackend.controller;

import gr.uoa.di.tedi.projectbackend.model.User;
import gr.uoa.di.tedi.projectbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// the controller gets client requests and uses the service

// TO GET USERS:
// - go to https://localhost:8443/login, and send a POST request containing JSON with username and password in body
// - go to headers of response, and copy the token under the Authorization header
// - go to /users, and on any request, add header with name "Authorization" and paste the token there.
// - if you logged in as an admin, you should receive the response

@RestController
@PreAuthorize("hasRole('admin')") // only users with the admin role can access users
class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users") // this runs if you send a GET request while on /users
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}") // this runs if you send a GET request while on /users/{id} etc
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = service.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/userslike/{name}")
    public ResponseEntity<List<User>> like(@PathVariable String name) {
        List<User> users = service.getUserLike(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User newUser) {
        User user = service.addUser(newUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping ("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        user = service.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) { service.deleteUser(id); }
}
