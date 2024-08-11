package com.sparta.msa_exam.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name="orders")
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> productIds = new ArrayList<>();

    @Builder
    public Order(String name) {
        this.name = name;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        this.productIds.add(orderProduct);
        orderProduct.setOrder(this);
    }

}
