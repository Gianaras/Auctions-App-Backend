package gr.uoa.di.tedi.projectbackend.model;

import antlr.debug.MessageAdapter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "reciever_id")
    private User receiver;

    @Column(name = "date_sent")
    private Timestamp date_sent;

    @Column(name = "is_read")
    private Boolean isRead;

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Timestamp getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(Timestamp date_sent) {
        this.date_sent = date_sent;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }


    public Message(){};


    public Message(User sender, User receiver , String message){
        this.sender=sender;
        this.receiver =receiver;
        this.message=message;
        this.date_sent = new Timestamp(System.currentTimeMillis());
        this.isRead = false;
    }


}