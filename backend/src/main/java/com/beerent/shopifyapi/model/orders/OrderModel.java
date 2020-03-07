package com.beerent.shopifyapi.model.orders;

import javax.persistence.*;

@Entity
@Table(name="orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "external_order_id", unique = true)
    private long externalOrderId;

    @Column(name = "user_id")
    private int userId;

    public OrderModel() {
    }

    public OrderModel(long externalOrderId, int user_id) {
        this.externalOrderId = externalOrderId;
        this.userId = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public long getExternalOrderId() {
        return externalOrderId;
    }

    public void setExternalOrderId(long externalOrderId) {
        this.externalOrderId = externalOrderId;
    }
}
