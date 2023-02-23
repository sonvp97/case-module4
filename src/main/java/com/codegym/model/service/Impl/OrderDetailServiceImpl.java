package com.codegym.model.service.Impl;

import com.codegym.model.entity.OrderDetail;
import com.codegym.model.repository.OrderDetailRepository;
import com.codegym.model.service.GeneralService;
import com.codegym.model.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public Iterable<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(Math.toIntExact(id));
    }

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);

    }

    @Override
    public void remove(Long id) {
        orderDetailRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public List<Integer> searchByOrderIdProduct(Integer id) {
        List<Integer> hashMap = orderDetailRepository.searchByOrderIdProductId(id);
        return hashMap;
    }

    @Override
    public List<Integer> searchByOrderIdQuantity(Integer id) {
        return orderDetailRepository.searchByOrderIdQuantity(id);
    }
}
