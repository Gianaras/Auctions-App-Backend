package gr.uoa.di.tedi.projectbackend.controller;

import gr.uoa.di.tedi.projectbackend.model.BidElement;
import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.model.Seller;
import gr.uoa.di.tedi.projectbackend.service.ItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

// everyone can access items, even without being logged in

@RestController
public class ItemsController {
    private final ItemsService service;

    public ItemsController(ItemsService service) {
        this.service = service;
    }

    @GetMapping("/items") // this runs if you send a GET request while on /items
    public ResponseEntity<List<Items>> getAllItems(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Items> items = service.getOngoingAuctions(now);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/itemsOfSeller/{sellerId}")
    public ResponseEntity<List<Items>> getItemsOfSeller(@PathVariable Long sellerId){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Items> items = service.getOngoingAuctionsOfSeller(now, sellerId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/sellerOfItems/{itemsId}") // this runs if you send a GET request while on /items
    public ResponseEntity<Seller> getSellerFromItems(@PathVariable Long itemsId){
        Seller seller = service.getSellerFromItems(itemsId);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/items/{id}") // this runs if you send a GET request while on /items/{id} etc
    public ResponseEntity<Items> getItem(@PathVariable Long id) {
        Items item = service.getItem(id);
        item.setStartedUTC(item.getStarted().getTime());
        item.setEndsUTC(item.getEnds().getTime());
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/items")
    @PreAuthorize("hasRole('user') || hasRole('admin')")
    public ResponseEntity<Items> addItem(@RequestBody Items newItem) {
        Items item = service.addItems(newItem);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping("/items/{id}")
    @PreAuthorize("hasRole('user') || hasRole('admin')")
    public ResponseEntity<Items> addBid(@PathVariable Long id, @RequestBody BidElement element) {
        Items item = service.addBid(id, element.amount, element.bidderName);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping ("/items/{id}")
    @PreAuthorize("hasRole('user') || hasRole('admin')")
    public ResponseEntity<Items> updateItem(@RequestBody Items items, @PathVariable Long id) {
        items = service.updateItem(id, items);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    @PreAuthorize("hasRole('user') || hasRole('admin')")
    public void deleteItem(@PathVariable Long id) { service.deleteItem(id); }

}
