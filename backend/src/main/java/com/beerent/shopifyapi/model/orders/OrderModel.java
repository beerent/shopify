package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.users.UserModel;

import javax.persistence.*;
import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserModel user_id;

    public OrderModel() {
    }

    public OrderModel(long externalOrderId, Date ordered, UserModel user_id) {
        this.externalOrderId = externalOrderId;
        this.ordered = ordered;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user_id;
    }

    public void setUser_id(UserModel user_id) {
        this.user_id = user_id;
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
}
