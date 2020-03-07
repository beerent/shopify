package com.beerent.shopifyapi.model.products;

import com.beerent.shopifyapi.model.orders.OrderProductMapModel;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderProductMapModel> orderProductMaps;

    public ProductModel() {
        this.orderProductMaps = new HashSet<OrderProductMapModel>();
    }

    public ProductModel(String name, Double price) {
        this.name = name;
        this.price = price;
        this.orderProductMaps = new HashSet<OrderProductMapModel>();
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

    public Set<OrderProductMapModel> getOrderProductMaps() {
        return orderProductMaps;
    }

    public void setOrderProductMaps(Set<OrderProductMapModel> orderProductMaps) {
        this.orderProductMaps = orderProductMaps;
    }
}
