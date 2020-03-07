package com.beerent.shopifyapi.model.orders;

import javax.persistence.*;
import com.beerent.shopifyapi.model.products.ProductModel;

@Entity
@Table(name="order_product_map")
public class OrderProductMapModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private OrderModel order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;

    public OrderProductMapModel() {
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
