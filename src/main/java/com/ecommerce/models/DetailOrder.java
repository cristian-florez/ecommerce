package com.ecommerce.models;

import jakarta.persistence.ManyToOne;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class DetailOrder {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_detail_order")
    private Integer idDetailOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_produc", nullable = false)
    private Product product;

    @Column(nullable = false, length = 250)
    private Integer quantity;

    @Column(nullable = false, length = 250)
    private Double price;

    public DetailOrder() {
    }

    public DetailOrder(Integer idDetailOrder, Order order, Product product, Integer quantity, Double price) {
        this.idDetailOrder = idDetailOrder;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
