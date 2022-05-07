package gr.uoa.di.tedi.projectbackend.users.repos;

import gr.uoa.di.tedi.projectbackend.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// this repository inherits from jpaRepository (basic ready-made functions like adding, deleting, updating users etc.)
// also inherits from CustomUserRepository, which contains extra functions we want to add ourselves

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

}
