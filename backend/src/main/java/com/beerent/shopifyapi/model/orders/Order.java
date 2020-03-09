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

    public void CopyNonUniqueFields(Order order) {
        this.ordered = order.getOrdered();
        this.externalId = order.getExternalId();
        this.user.CopyNonUniqueFields(order.getUser());
        CopyProductNonUniqueFields(order);
    }

    private void CopyProductNonUniqueFields(Order order) {
        Map<String, OrderProductMap> targetProducts = new HashMap<String, OrderProductMap>();
        for (OrderProductMap product : order.getProducts()) {
            targetProducts.put(product.getProduct().getExternalId(), product);
        }

        for (OrderProductMap product : this.products) {
            OrderProductMap targetProduct = targetProducts.get(product.getProduct().getExternalId());
            if (targetProduct != null) {
                product.CopyNonUniqueFields(targetProduct);
            }
        }
    }

}
