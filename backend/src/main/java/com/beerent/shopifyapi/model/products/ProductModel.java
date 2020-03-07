package com.beerent.shopifyapi.model.products;

import com.beerent.shopifyapi.model.orders.OrderModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    //@OneToMany(cascade = CascadeType.ALL)
    //private Set<OrderModel> orders;

    public ProductModel(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    //public Set<OrderModel> getOrders() {
    //    return orders;
    //}

    //public void setOrders(Set<OrderModel> orders) {
    //    this.orders = orders;
    //}
}
