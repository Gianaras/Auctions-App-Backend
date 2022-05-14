package gr.uoa.di.tedi.projectbackend.service;


import gr.uoa.di.tedi.projectbackend.handling.SellerNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.Seller;
import gr.uoa.di.tedi.projectbackend.repos.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {
    private final SellerRepository repository;

    @Autowired
    public SellerService(SellerRepository repository){ this.repository = repository; }

    public Seller getSeller(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException(id));
    }

    public Seller addSeller(Seller newSeller) { return repository.save(newSeller); }

    public void deleteSeller(Long id) { repository.deleteById(id); }

    public Seller updateUser(Seller seller) { return repository.save(seller); }

    public List<Seller> getAllSellers() { return repository.findAll(); }


}
