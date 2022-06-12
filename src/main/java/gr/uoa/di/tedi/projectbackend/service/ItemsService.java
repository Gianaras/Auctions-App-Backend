package gr.uoa.di.tedi.projectbackend.service;
import gr.uoa.di.tedi.projectbackend.handling.CategoryNotFoundException;
import gr.uoa.di.tedi.projectbackend.handling.ItemNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.*;
import gr.uoa.di.tedi.projectbackend.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;
    private final ItemRepository itemRepository;
    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    private final ImageRepository imageRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository, ItemRepository itemRepository, BidRepository bidRepository,
                        UserRepository userRepository, CategoryRepository categoryRepository,
                        LocationRepository locationRepository,ImageRepository imageRepository) {
        this.itemsRepository = itemsRepository;
        this.itemRepository = itemRepository;
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.imageRepository = imageRepository;
    }

    public Items addItems(Items newItems) {
        locationRepository.save(newItems.getLocation()); // save new location
        itemsRepository.save(newItems); // save new items

        // apply categories
        for (Category categoryTmp : newItems.getCategories()) {
            String categoryId = categoryTmp.getId(); // get category name of input category
            Category category = categoryRepository.findById(categoryId) // use id to get actual category in db
                            .orElseThrow(() -> new CategoryNotFoundException(categoryId));
            category.getItems().add(newItems);
            categoryRepository.save(category);
        }

        // add item objects
        Set<Item> itemsTmp = new HashSet<>(newItems.getItems());
        for (Item item : itemsTmp) {
            Item newItem = new Item(item.getName(), item.getDescription(), newItems);
            itemRepository.save(newItem);
            System.out.println("aaaaaaaaaaaaaaa");
            for(Image image:item.getImages()){
                Image newImage = new Image(newItem,image.getImage_data(),image.getImage_name());
                System.out.println("test2 "+image.getImage_data());
                imageRepository.save(newImage);
            }
            addNewItem(newItems, newItem);
        }
        return newItems;
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
        bidRepository.save(bid);

        items.addBid(bid);
        return itemsRepository.save(items);
    }

    public void deleteItem(Long id) {
        Items items = getItem(id);

        // remove relations with categories
        for (Category category : items.getCategories()) {
            category.getItems().remove(items);
            categoryRepository.save(category);
        }

        // automatically deletes Items of auction as well
        itemsRepository.deleteById(id);
    }

    public List<Items> getAllItems() { return itemsRepository.findAll(); }

    public Items getItem(Long id) {
        return itemsRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(id));
    }

    public Items updateItem(Long itemsId, Items items) {
        deleteItem(itemsId); // delete previous
        Items newItems = addItems(items); // update

        // remove garbage null items of return value
        Set<Item> itemsTmp = new HashSet<>(newItems.getItems());
        for (Item item : itemsTmp)
            if (item.getId() == null) newItems.getItems().remove(item);
        return newItems;
    }

    public List<Items> getOngoingAuctions(Timestamp current){
        return itemsRepository.getOngoingItems(current);
    }

    public List<Items> getOngoingAuctionsOfSeller(Timestamp current, Long sellerId) {
        return itemsRepository.getOngoingItemsOfSeller(current, sellerId);
    }

    public Seller getSellerFromItems(Long itemsId) {
        return itemsRepository.getSellerFromItemsId(itemsId);
    }
}
