package com.codegym.model.service.Impl;

import com.codegym.model.entity.Order;
import com.codegym.model.repository.OrderRepository;
import com.codegym.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(Math.toIntExact(id));
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);

    }

    @Override
    public void remove(Long id) {
        orderRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public Order findByCustomerId(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
