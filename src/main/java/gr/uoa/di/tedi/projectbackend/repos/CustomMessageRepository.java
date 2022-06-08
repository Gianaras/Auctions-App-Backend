package gr.uoa.di.tedi.projectbackend.repos;


import gr.uoa.di.tedi.projectbackend.model.Message;
import gr.uoa.di.tedi.projectbackend.model.User;

import java.util.List;

public interface CustomMessageRepository {

    List<Message> getUserReceived(String username);
    List<User> getRelevantUsers(String username);
}
