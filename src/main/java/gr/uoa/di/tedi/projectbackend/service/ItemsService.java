package gr.uoa.di.tedi.projectbackend.service;
import gr.uoa.di.tedi.projectbackend.handling.ItemNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.repos.ItemsRepository;
import gr.uoa.di.tedi.projectbackend.repos.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository, ItemRepository itemRepository) {
        this.itemsRepository = itemsRepository;
        this.itemRepository = itemRepository;
    }

    public Items addItem(Items newItems) {
        itemsRepository.save(newItems);
        itemRepository.saveAll(newItems.getItems());
        return itemsRepository.save(newItems);
    }

    public void deleteItem(Long id) { itemsRepository.deleteById(id); }

    public List<Items> getAllItems(){return itemsRepository.findAll();}

    public Items getItem(Long id){
        return itemsRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(id));
    }

    public Items updateItem(Items item) { return itemsRepository.save(item);}

}
