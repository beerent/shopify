package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.products.ProductModel;
import com.beerent.shopifyapi.model.users.UserModel;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "external_order_id", unique = true)
    private long externalOrderId;

    @Column(name = "ordered")
    private Date ordered;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(columnDefinition="integer", name = "user_id")
    private UserModel user;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ProductModel> products;

    //@OneToMany(cascade = CascadeType.ALL)
    //OrderProductMapModel orderProductMap;

    //

    public OrderModel() {
        //this.products = new HashSet<ProductModel>();
    }

    public OrderModel(long externalOrderId, Date ordered, UserModel user) {
        this.externalOrderId = externalOrderId;
        this.ordered = ordered;
        this.user = user;

        //this.products = new HashSet<ProductModel>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
        //user.addOrder(this);
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

    public Set<ProductModel> getProducts() {
        return this.products;
    }

    public void setProducts(Set<ProductModel> products) {
        this.products = products;
    }
}
