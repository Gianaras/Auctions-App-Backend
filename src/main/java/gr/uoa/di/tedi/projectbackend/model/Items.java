package gr.uoa.di.tedi.projectbackend.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "items_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "current_bid")
    private Double currentBid;

    @Column(name = "buy_price")
    private Double buyPrice;

    @Column(name = "first_bid")
    private Double firstBid;

    @Column(name = "number_of_bids")
    private Integer numberOfBids;

    @Column(name = "started")
    private Timestamp started;
    private Long startedUTC;

    @Column(name = "ends")
    private Timestamp ends;
    private Long endsUTC;

    @OneToMany(mappedBy = "items", orphanRemoval = true)
    private Set<Bid> bids = new LinkedHashSet<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToMany(mappedBy = "items")
    private Set<Category> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "items", orphanRemoval = true)
    private Set<Item> items = new LinkedHashSet<>();

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Long getStartedUTC() { return startedUTC; }

    public void setStartedUTC(Long startedUTC) { this.startedUTC = startedUTC; }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public Timestamp getEnds() {
        return ends;
    }

    public Long getEndsUTC() { return endsUTC; }

    public void setEndsUTC(Long endsUTC) { this.endsUTC = endsUTC; }

    public void setEnds(Timestamp ends) {
        this.ends = ends;
    }

    public Timestamp getStarted() {
        return started;
    }

    public void setStarted(Timestamp started) {
        this.started = started;
    }

    public Integer getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(Integer numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    public Double getFirstBid() {
        return firstBid;
    }

    public void setFirstBid(Double firstBid) {
        this.firstBid = firstBid;
    }

    public Double getBuyPrice() { return buyPrice; }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addBid(Bid bid) {
        this.getBids().add(bid);
        if (bid.getAmount() > this.getCurrentBid()) {
            this.setCurrentBid(bid.getAmount());
            this.setNumberOfBids(this.getNumberOfBids()+1);
        }
    }

    public Items(double buyPrice , double currentBid ,double firstBid  , Timestamp started ,
                 Timestamp ends,int numberOfBids){
        this.buyPrice=buyPrice;
        this.currentBid=currentBid;
        this.firstBid=firstBid;
        this.started=started;
        this.ends=ends;
        this.numberOfBids=numberOfBids;
    }

    public Items(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Items items = (Items) o;
        return id != null && Objects.equals(id, items.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}