package gr.uoa.di.tedi.projectbackend.service;
import gr.uoa.di.tedi.projectbackend.handling.ItemNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.Bid;
import gr.uoa.di.tedi.projectbackend.model.Item;
import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.model.User;
import gr.uoa.di.tedi.projectbackend.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;
    private final ItemRepository itemRepository;
    private final BidRepository bidRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository, ItemRepository itemRepository, BidRepository bidRepository,
                        UserRepository userRepository) {
        this.itemsRepository = itemsRepository;
        this.itemRepository = itemRepository;
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
    }

    public Items addItems(Items newItems) {
        return itemsRepository.save(newItems);
    }

    public Items addNewItem(Items items, Item newItem){
        items.getItems().add(newItem);
        return itemsRepository.save(items);
    }

    public Items addBid(Long itemsId, Double amount, String bidderName) {
        long now = System.currentTimeMillis();
        Items items = getItem(itemsId);
        User user = userRepository.findByUsername(bidderName);
        Bid bid = new Bid(items, user.getBidder(), amount, new Timestamp(now));
        System.out.println("user get bidder " + user.getBidder().getUser().getId());
        bidRepository.save(bid);
        System.out.println("saved bid");

        items.addBid(bid);
        System.out.println("added bid to items");
        return itemsRepository.save(items);
    }

    public void deleteItem(Long id) { itemsRepository.deleteById(id); }

    public List<Items> getAllItems(){return itemsRepository.findAll();}

    public Items getItem(Long id){
        return itemsRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(id));
    }

    public Items updateItem(Items item) { return itemsRepository.save(item);}

}
