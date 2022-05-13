package gr.uoa.di.tedi.projectbackend.users.controller;


import gr.uoa.di.tedi.projectbackend.users.model.Items;
import gr.uoa.di.tedi.projectbackend.users.service.ItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemsController {
    private final ItemsService service;

    public ItemsController(ItemsService service) {
        this.service = service;
    }

    @GetMapping("/items") // this runs if you send a GET request while on /users
    public ResponseEntity<List<Items>> getAllItems(){
        List<Items> items = service.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/items/{id}") // this runs if you send a GET request while on /users/{id} etc
    public ResponseEntity<Items> getItem(@PathVariable Long id) {
        Items item = service.getItem(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<Items> addItem(@RequestBody Items newItem) {
        Items item = service.addItem(newItem);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @PutMapping ("/items/{id}")
    public ResponseEntity<Items> updateItem(@RequestBody Items Item) {
        Item = service.updateItem(Item);
        return new ResponseEntity<>(Item, HttpStatus.OK);
    }
    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) { service.deleteItem(id); }

}
