package com.ecommerce.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Integer idProduct;
    
    @Column(nullable = false, length = 250)
    private String name;

    @Column(nullable = false, length = 250)
    private String description;

    @Column(nullable = false, length = 250)
    private double price;

    @Column(nullable = false, length = 250)
    private Integer stock;

    @Column(nullable = false, length = 250)
    private String category;

    @Column(nullable = false, length = 250)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "product")
    private List<DetailOrder> detailOrders;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts;

    public Product() {
    }

    public Product(Integer idProduct, String name, String description, double price, Integer stock, String category, LocalDate createdAt, List<DetailOrder> detailOrders, List<Cart> carts) {
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.createdAt = createdAt;
        this.detailOrders = detailOrders;
        this.carts = carts;
    }

    public Product(String name, String description, double price, Integer stock, String category, LocalDate createdAt){
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.createdAt = createdAt;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<DetailOrder> getDetailOrders() {
        return detailOrders;
    }

    public void setDetailOrders(List<DetailOrder> detailOrders) {
        this.detailOrders = detailOrders;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

}
