package com.ecommerce.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders") // Cambia el nombre para evitar conflictos con la palabra reservada de SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Integer idOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(nullable = false, length = 250)
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 250)
    private StateEnum status;

    @Column(nullable = false, length = 250)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<DetailOrder> orderDetails;

    public Order() {
    }

    public Order(Integer idOrder, User user, Double total, StateEnum status, LocalDate createdAt, List<DetailOrder> orderDetails) {
        this.idOrder = idOrder;
        this.user = user;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
        this.orderDetails = orderDetails;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public StateEnum getStatus() {
        return status;
    }

    public void setStatus(StateEnum status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<DetailOrder> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<DetailOrder> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
