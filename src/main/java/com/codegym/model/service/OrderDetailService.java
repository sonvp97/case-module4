package com.codegym.model.service;

import com.codegym.model.entity.OrderDetail;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;

public interface OrderDetailService extends GeneralService<OrderDetail>{
    List<Integer> searchByOrderIdProduct(Integer id);
    List<Integer> searchByOrderIdQuantity(Integer id);
}
