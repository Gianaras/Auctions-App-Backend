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


    //===============Inbox functs===============


    @GetMapping("/messages/inbox/{username}")
    //only the user himself and admins can see received messages
    @PreAuthorize("(hasRole('user') && authentication.name == #username ) || hasRole('admin') ")
    public ResponseEntity<List<MessageElement>> getUserReceived(@PathVariable String username){
        List<MessageElement> messages = service.getUserReceived(username);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }


    //Used to mark messages as read
    @PutMapping("/messages/inbox/{username}")
    @PreAuthorize("(hasRole('user') && authentication.name == #username) || hasRole('admin') ")
    public ResponseEntity updateMessage(@PathVariable String username,@RequestBody MessageElement messageElement){
        Message message = service.getMessage(messageElement.id);

        if(!message.getReceiver().getUsername().equals(username)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        message.setIsRead(true);
        service.updateMessage(message);
        return new ResponseEntity(HttpStatus.OK);
    }

    // ===============Send messages functs===============



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


//===============Outbox functs===============

    @GetMapping("/messages/outbox/{username}")
    //only the user himself and admins can see sent messages
    @PreAuthorize("(hasRole('user') && authentication.name == #username ) || hasRole('admin') ")
    public ResponseEntity<List<MessageElement>> getUserSent(@PathVariable String username){
        List<MessageElement> messages = service.getUserSent(username);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }


    //===============General===============

 @DeleteMapping("/messages/{username}/{messageID}")
 @PreAuthorize("(hasRole('user') && authentication.name == #username ) || hasRole('admin') ")
    public ResponseEntity deleteMessage(@PathVariable("username") String username,@PathVariable("messageID") Integer messageID){
        Message message = service.getMessage(messageID);
        if(!message.getSender().getUsername().equals(username) && !message.getReceiver().getUsername().equals(username) ){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        service.deleteMessage(message.getId());
        return new ResponseEntity(HttpStatus.OK);
 }

}
