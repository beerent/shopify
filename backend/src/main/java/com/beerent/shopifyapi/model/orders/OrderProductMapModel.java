package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.products.ProductModel;

import javax.persistence.*;

@Entity
@Table(name="order_product_map")
public class OrderProductMapModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "order_id")
    private OrderModel order;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private ProductModel product;

    public OrderProductMapModel() {

    }

    public OrderProductMapModel(OrderModel order_id, ProductModel product_id) {
        this.order = order_id;
        this.product = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }
}
