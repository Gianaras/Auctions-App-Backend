package gr.uoa.di.tedi.projectbackend.model;

import lombok.Data;

import javax.persistence.*;

// spring automatically generates getters and setters for below fields

@Data
@Entity
public class User {
    private @Id @GeneratedValue Long id;        // the id is generated automatically, and it's the primary key
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String country;
    private String city;
    private boolean admin;
    private boolean activated;


    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Bidder bidder;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Seller seller;

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }


    public User() {}

    public User(String username, String password, String fName, String lName, String email, String phone,
                String address, String country, String city, boolean admin, boolean activated) {
        this.username = username;
        this.password = password;
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.country = country;
        this.city = city;
        this.admin = admin;
        this.activated = activated;
    }
}
