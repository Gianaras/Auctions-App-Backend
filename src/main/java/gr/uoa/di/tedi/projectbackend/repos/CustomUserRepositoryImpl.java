package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

// Implementation of extra functions that interact with the database

@Repository
@Transactional(readOnly = true)
public class CustomUserRepositoryImpl implements CustomUserRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override // returns list of users with usernames similar to given string (I think)
    public List<User> getUsernameLike(String name) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username LIKE ?1");
        query.setParameter(1, "%" + name + "%");
        return query.getResultList();
    }

    @Override // returns list of admins (should only be 1 admin)
    public List<User> getAdmin() {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.admin = true");
        return query.getResultList();
    }
}
