package com.codegym.model.service;

import com.codegym.model.entity.Order;

public interface OrderService extends GeneralService<Order>{
    Order findByCustomerId(Integer customerId);
}
