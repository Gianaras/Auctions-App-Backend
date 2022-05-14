package gr.uoa.di.tedi.projectbackend.service;


import gr.uoa.di.tedi.projectbackend.handling.BidderNotFoundException;
import gr.uoa.di.tedi.projectbackend.handling.UserNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.Bidder;
import gr.uoa.di.tedi.projectbackend.repos.BidderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidderService {
    private final BidderRepository repository;

    @Autowired
    public BidderService(BidderRepository repository){ this.repository = repository; }

    public Bidder getBidder(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BidderNotFoundException(id));
    }

    public Bidder addBidder(Bidder newBidder) { return repository.save(newBidder); }

    public void deleteBidder(Long id) { repository.deleteById(id); }

    public Bidder updateUser(Bidder bidder) { return repository.save(bidder); }

    public List<Bidder> getAllBidders() { return repository.findAll(); }


}
