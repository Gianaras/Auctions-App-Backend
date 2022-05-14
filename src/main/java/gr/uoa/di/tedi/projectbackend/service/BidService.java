package gr.uoa.di.tedi.projectbackend.service;


import gr.uoa.di.tedi.projectbackend.handling.BidNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.Bid;
import gr.uoa.di.tedi.projectbackend.repos.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {
    private final BidRepository repository;

    @Autowired
    public BidService(BidRepository repository){ this.repository = repository; }

    public Bid getBid(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BidNotFoundException(id));
    }

    public Bid addBid(Bid newBid) { return repository.save(newBid); }

    public void deleteBid(Long id) { repository.deleteById(id); }

    public Bid updateUser(Bid bid) { return repository.save(bid); }

    public List<Bid> getAllBids() { return repository.findAll(); }


}
