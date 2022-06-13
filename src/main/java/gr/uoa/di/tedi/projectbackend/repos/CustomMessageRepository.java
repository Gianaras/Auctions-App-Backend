package gr.uoa.di.tedi.projectbackend.repos;


import gr.uoa.di.tedi.projectbackend.model.Message;
import gr.uoa.di.tedi.projectbackend.model.MessageElement;
import gr.uoa.di.tedi.projectbackend.model.User;

import java.util.List;

public interface CustomMessageRepository {

    List<MessageElement> getUserReceived(String username);
    List<User> getRelevantUsers(String username);
    List<Message> getUserRelated(String username);

    List<MessageElement> getUserSent(String username);
}
