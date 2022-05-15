package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Bid;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CustomBidderRepositoryImpl implements CustomBidderRepository{
    @PersistenceContext
    EntityManager entityManager;

    //on second thought these queries might not be needed since we can use (bidder).getBids()
    @Override
    public List<Bid> getBidsOfBidder(Long bidderID) {
        Query query = entityManager.createQuery("SELECT bid FROM Bid bid , Bidder bidder WHERE bidder.id=?1 AND bidder=bid.bidder");
        query.setParameter(1, "%" + bidderID + "%");
        return query.getResultList();
    }

    @Override
    public List<Bid> getBidsOnItem(Long itemID){
        Query query = entityManager.createQuery("SELECT bid FROM Bid bid,Items item WHERE bid.items=item AND item.id=?1");
        query.setParameter(1,"%" + itemID + "%");
        return query.getResultList();
    }

    @Override
    public List<Bid> getBidsOfBidderOnItem(Long bidderID,Long itemID){
        List<Bid> bidderBids = getBidsOfBidder(bidderID);
        List<Bid> bidsOnItem = getBidsOnItem(itemID);
        List<Bid> result = new ArrayList<Bid>(bidderBids);
        result.retainAll(bidsOnItem);
        return result;
    }

}
