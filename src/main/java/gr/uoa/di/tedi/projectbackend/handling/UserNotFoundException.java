package gr.uoa.di.tedi.projectbackend.handling;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not find User " + id);
    }
}
