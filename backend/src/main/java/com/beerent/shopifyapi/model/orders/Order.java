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

    @Column(name = "external_id", unique = true)
    private String externalId;

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

    public Order(String externalId, Date ordered, User user) {
        this.externalId = externalId;
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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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

    public void updateProducts(Set<OrderProductMap> products) {
        for (OrderProductMap n : products) {
            for (OrderProductMap o : this.products) {
                if (n.getProduct().getName().equals(o.getProduct().getName())) {
                    o.setQuantity(n.getQuantity());
                    break;
                }
            }
        }
    }

    public void update(Order order) {
        this.ordered = order.getOrdered();
        this.externalId = order.getExternalId();
        this.user.update(order.getUser());

        for (OrderProductMap product : this.products) {
            for (OrderProductMap newProduct : order.getProducts()) {
                if (product.getProduct().getName().equals(newProduct.getProduct().getName())) {
                    product.update(newProduct);
                    break;
                }
            }
        }
    }
}
