package gr.uoa.di.tedi.projectbackend.users.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
