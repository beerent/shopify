package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.users.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "external_order_id", unique = true)
    private long externalOrderId;

    @Column(name = "ordered")
    private Date ordered;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(columnDefinition="integer", name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Set<OrderProductMap> products;

    public Order() {
        this.products = new HashSet<OrderProductMap>();
    }

    public Order(long externalOrderId, Date ordered, User user) {
        this.externalOrderId = externalOrderId;
        this.ordered = ordered;
        this.user = user;
        this.products = new HashSet<OrderProductMap>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getExternalOrderId() {
        return externalOrderId;
    }

    public void setExternalOrderId(long externalOrderId) {
        this.externalOrderId = externalOrderId;
    }

    public Date getOrdered() {
        return ordered;
    }

    public void setOrdered(Date ordered) {
        this.ordered = ordered;
    }

    public Set<OrderProductMap> getProducts() {
        return this.products;
    }

    public void setProducts(Set<OrderProductMap> products) {
        this.products = products;
    }
}
