package com.beerent.shopifyapi.model.orders;

import com.beerent.shopifyapi.model.products.Product;

import javax.persistence.*;

@Entity
@Table(name="order_product_map")
public class OrderProductMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Long quantity;


    public OrderProductMap() {
    }

    public OrderProductMap(Order order, Product product, Long quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void CopyNonUniqueFields(OrderProductMap newProduct) {
        this.quantity = newProduct.getQuantity();
        this.product.update(newProduct.getProduct());
    }
}
