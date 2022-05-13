package gr.uoa.di.tedi.projectbackend.users.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "bidder")
public class Bidder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "bidder_rating")
    private Double bidderRating;

    @OneToMany(mappedBy = "bidder", orphanRemoval = true)
    private Set<Bid> bids = new LinkedHashSet<>();

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public Double getBidderRating() {
        return bidderRating;
    }

    public void setBidderRating(Double bidderRating) {
        this.bidderRating = bidderRating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}