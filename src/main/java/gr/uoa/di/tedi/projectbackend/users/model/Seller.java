package gr.uoa.di.tedi.projectbackend.users.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "seller_rating")
    private Double sellerRating;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "seller", orphanRemoval = true)
    private Set<Items> items = new LinkedHashSet<>();

    public Set<Items> getItems() {
        return items;
    }

    public void setItems(Set<Items> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(Double sellerRating) {
        this.sellerRating = sellerRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seller(){}

    public Seller(User user,double sellerRating){
        this.sellerRating= sellerRating;
        this.user = user;
    }

}