package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Items;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CustomSellerRepositoryImpl implements CustomSellerRepository{
    @PersistenceContext
    EntityManager entityManager;


    public List<Items> getListings(Long sellerID) {
        Query query = entityManager.createQuery("SELECT item FROM Items item , Seller seller WHERE seller.id=?1 AND seller=item.seller");
        query.setParameter(1, "%" + sellerID + "%");
        return query.getResultList();
    }


}
