package com.ing.broker.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String asset;
    
    @Enumerated(EnumType.STRING)
    private OrderSide side;  // BUY or SELL

    private int size;  // Number of shares

    private double price;  // Price per share

    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // PENDING, MATCHED, CANCELED
    
    private LocalDateTime orderDate;

}
