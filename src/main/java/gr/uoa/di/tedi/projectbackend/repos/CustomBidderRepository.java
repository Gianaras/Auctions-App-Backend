package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Bid;

import java.util.List;

public interface CustomBidderRepository {
    public List<Bid> getBidsOfBidder(Long bidderID);

    List<Bid> getBidsOnItem(Long itemID);

    List<Bid> getBidsOfBidderOnItem(Long bidderID, Long itemID);
}
