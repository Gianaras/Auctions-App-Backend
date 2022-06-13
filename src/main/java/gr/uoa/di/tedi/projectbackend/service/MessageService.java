package gr.uoa.di.tedi.projectbackend.service;


import gr.uoa.di.tedi.projectbackend.handling.MessageNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.Message;
import gr.uoa.di.tedi.projectbackend.model.MessageElement;
import gr.uoa.di.tedi.projectbackend.model.User;
import gr.uoa.di.tedi.projectbackend.repos.MessageRepository;
import gr.uoa.di.tedi.projectbackend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final UserRepository userRepository;
    private final MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository, UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Message getMessage(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));
    }

    public Message addMessage(Message newMessage) { return repository.save(newMessage); }

    public Message addMessage(String sender,String receiver ,String content) {
        Message newMessage = new Message(userRepository.findByUsername(sender),userRepository.findByUsername(receiver),content);
        return repository.save(newMessage);
    }


    public void deleteMessage(Integer id) { repository.deleteById(id); }

    public Message updateMessage(Message message) { return repository.save(message); }

    public List<Message> getAllMessages() { return repository.findAll(); }

    public List<MessageElement> getUserReceived(String username){return repository.getUserReceived(username);}

    public List<User> getRelevantUsers(String username){return repository.getRelevantUsers(username);}

    public List<MessageElement> getUserSent(String username) {return repository.getUserSent(username);}
}
