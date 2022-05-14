package gr.uoa.di.tedi.projectbackend.service;
import gr.uoa.di.tedi.projectbackend.handling.ItemNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.repos.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsService {
    private final ItemsRepository repository;


    @Autowired
    public ItemsService(ItemsRepository repository){this.repository=repository;}


    public Items addItem(Items newItem){return repository.save(newItem);}

    public void deleteItem(Long id) { repository.deleteById(id); }

    public List<Items> getAllItems(){return repository.findAll();}

    public Items getItem(Long id){
        return repository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(id));
    }

    public Items updateItem(Items item) { return repository.save(item);}

}
