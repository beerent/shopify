package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.users.UserModel;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @PrimaryKeyJoinColumn(name = "user_id")
    private UserModel user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderProductMapModel> orderProductMaps;

    public OrderModel() {
        this.orderProductMaps = new HashSet<OrderProductMapModel>();
    }

    public OrderModel(long externalOrderId, Date ordered, UserModel user) {
        this.externalOrderId = externalOrderId;
        this.ordered = ordered;
        this.user = user;
        this.orderProductMaps = new HashSet<OrderProductMapModel>();
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

    public Set<OrderProductMapModel> getOrderProductMaps() {
        return orderProductMaps;
    }

    public void setOrderProductMaps(Set<OrderProductMapModel> orderProductMaps) {
        this.orderProductMaps = orderProductMaps;
    }
}
