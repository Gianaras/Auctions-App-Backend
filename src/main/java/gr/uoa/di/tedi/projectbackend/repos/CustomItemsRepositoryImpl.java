package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.model.Seller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CustomItemsRepositoryImpl implements CustomItemsRepository{
    @PersistenceContext
    EntityManager entityManager;

    public List<Items> getOngoingItems(Timestamp current){
        Query query = entityManager.createQuery("SELECT items FROM Items items  WHERE items.ends >  :current AND items.currentBid < items.buyPrice");
        query.setParameter("current",current);
        return query.getResultList();
    }

    public List<Items> getOngoingItemsOfSeller(Timestamp current, Long sellerId) {
        Query query = entityManager.createQuery("SELECT items FROM Seller seller, Items items " +
                        "WHERE seller.id = items.seller.id AND seller.id = :sellerId AND items.ends > :current");
        query.setParameter("sellerId", sellerId);
        query.setParameter("current",current);
        return query.getResultList();
    }

    public Seller getSellerFromItemsId(Long itemsId) {
        Query query = entityManager.createQuery("SELECT seller FROM Seller seller, Items items " +
                "WHERE seller.id = items.seller.id AND items.id = :itemsId");
        query.setParameter("itemsId", itemsId);

        if (query.getResultList().isEmpty()) return null;
        return (Seller) query.getResultList().get(0);
    }
}
