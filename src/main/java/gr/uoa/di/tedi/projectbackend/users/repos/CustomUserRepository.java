package gr.uoa.di.tedi.projectbackend.users.repos;

import gr.uoa.di.tedi.projectbackend.users.model.User;
import java.util.List;

// Interface for extra functions that interact with the database
public interface CustomUserRepository {
    List<User> getUsernameLike(String name);
}
