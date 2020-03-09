package com.beerent.shopifyapi.model.products;

import com.beerent.shopifyapi.model.orders.OrderModel;
import com.beerent.shopifyapi.model.orders.OrderProductMap;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Set<OrderProductMap> products;

    public ProductModel() {
        this.products = new HashSet<OrderProductMap>();
    }

    public ProductModel(String name, Double price) {
        this.name = name;
        this.price = price;
        this.products = new HashSet<OrderProductMap>();
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

    public void addOrderProduct(OrderProductMap opr) {
        this.products.add(opr);
    }

    public Set<OrderProductMap> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderProductMap> products) {
        this.products = products;
    }
}
