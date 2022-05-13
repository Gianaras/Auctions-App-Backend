package gr.uoa.di.tedi.projectbackend.users.model;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bidder_id")
    private Bidder bidder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "items_id")
    private Items items;

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bid(){}

    public Bid(Items item, Bidder bidder, double amount, Timestamp time){
        this.amount = amount;
        this.bidder = bidder;
        this.items = item;
        this.time = time;
    }

}