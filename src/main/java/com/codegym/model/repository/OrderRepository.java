package com.codegym.model.repository;

import com.codegym.model.entity.Order;
import com.codegym.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByCustomerId(Integer customerId);

}
