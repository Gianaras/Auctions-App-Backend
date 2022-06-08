package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.model.Message;
import gr.uoa.di.tedi.projectbackend.model.MessageElement;
import gr.uoa.di.tedi.projectbackend.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class CustomMessageRepositoryImpl implements CustomMessageRepository{

    @PersistenceContext
    EntityManager entityManager;


    public List<MessageElement> getUserReceived(String username){
        Query query = entityManager.createQuery("SELECT message FROM Message message WHERE message.receiver.username = :username ");
        query.setParameter("username",username);
        List<Message> messages = query.getResultList();
        List<MessageElement> ret = new ArrayList<>();
        for(Message message:messages){
            MessageElement temp =  new MessageElement();
            temp.receiver = message.getReceiver().getUsername();
            temp.sender = message.getSender().getUsername();
            temp.content = message.getMessage();
            ret.add(temp);
        }
        return ret;
    }


    //a user is "relevant" with another user if they have won one of the users auctions or vice versa
    public List<User> getRelevantUsers(String username){
        Timestamp current_time =  new Timestamp(System.currentTimeMillis());

        //get relevant bidders
                                                    //select the bidder        from bidders and auctions      where given username is the seller of the auction
        Query query = entityManager.createQuery("SELECT bids.bidder.user FROM Bid bids , Items auction WHERE auction.seller.user.username = :username "+
                            //the bidder holds the current bid     AND     the bid is for the current auction     AND    the auction is over
                            "AND bids.amount = auction.currentBid AND   auction.id=bids.items.id            AND (auction.buyPrice <= auction.currentBid OR :current_time > auction.ends)"

                );
        query.setParameter("username",username);
        query.setParameter("current_time",current_time);
        List<User> Bidders = query.getResultList();

        //get relevant Sellers
        query = entityManager.createQuery("SELECT auction.seller.user FROM Bid bids , Items auction WHERE bids.amount = auction.currentBid AND auction.id = bids.items.id " +
                "AND bids.bidder.user.username = :username AND (auction.buyPrice <= auction.currentBid OR :current_time > auction.ends) ");
        query.setParameter("username",username);
        query.setParameter("current_time",current_time);
        List<User> Sellers = query.getResultList();

        //return union of relevant sellers and bidders
        Set<User> relevant = new HashSet<>();
        relevant.addAll(Sellers);
        relevant.addAll(Bidders);
        return new ArrayList<>(relevant);
    }

}
