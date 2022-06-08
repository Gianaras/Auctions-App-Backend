package gr.uoa.di.tedi.projectbackend.controller;


import gr.uoa.di.tedi.projectbackend.model.Message;
import gr.uoa.di.tedi.projectbackend.model.MessageElement;
import gr.uoa.di.tedi.projectbackend.model.User;
import gr.uoa.di.tedi.projectbackend.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    public final MessageService service;

    public MessageController(MessageService service){this.service = service;}

    @GetMapping("/messages/inbox/{username}")
    //only the user himself and admins can see received messages
    @PreAuthorize("(hasRole('user') && authentication.name == #username ) || hasRole('admin') ")
    public ResponseEntity<List<MessageElement>> getUserReceived(@PathVariable String username){
        List<MessageElement> messages = service.getUserReceived(username);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }




    @GetMapping("/messages/send/{username}")
    //only the user himself and admins can see received messages
    @PreAuthorize("(hasRole('user') && authentication.name == #username ) || hasRole('admin') ")
    public ResponseEntity<List<User>> getRelevantUsers(@PathVariable String username){
        List<User> users = service.getRelevantUsers(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }




    @PostMapping("/messages/send/{sender}/{receiver}")
    @PreAuthorize("(hasRole('user') && authentication.name == #sender) || hasRole('admin') ")                          //message content
    public ResponseEntity<MessageElement> sendMessage(@PathVariable("sender") String sender , @PathVariable("receiver") String receiver ,@RequestBody MessageElement messageElement){
        List<User> relevantUsers = service.getRelevantUsers(sender);
        for(User u:relevantUsers){
            if(u.getUsername().equals(receiver)){
                service.addMessage(sender,receiver,messageElement.content);
                messageElement.receiver=receiver;
                messageElement.sender=sender;
                System.out.println("Message successfuly sent!");
                return new ResponseEntity<>(messageElement,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
