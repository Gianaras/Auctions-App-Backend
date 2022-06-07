package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Items;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CustomItemsRepositoryImpl implements CustomItemsRepository{
    @PersistenceContext
    EntityManager entityManager;

    public List<Items> getOngoingItems(Timestamp current){
        Query query = entityManager.createQuery("SELECT items FROM Items items  WHERE items.ends >  :current ");
        query.setParameter("current",current);
        return query.getResultList();
    }
}
