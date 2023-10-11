package com.hoaxify.orderservice.repository;

import com.hoaxify.orderservice.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
