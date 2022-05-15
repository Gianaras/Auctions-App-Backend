package gr.uoa.di.tedi.projectbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "item_belongs_to_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Items> items = new LinkedHashSet<>();

    public Set<Items> getItems() {
        return items;
    }

    public void setItems(Set<Items> items) {
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category(){}

    public Category(String categoryName){
        this.id=categoryName;
    }

}