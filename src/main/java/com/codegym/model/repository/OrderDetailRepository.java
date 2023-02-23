package com.codegym.model.repository;

import com.codegym.model.entity.OrderDetail;
import com.codegym.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query(nativeQuery = true,
    value = "SELECT product_id " +
            "from orderdetail " +
            "where order_id = :id " +
            "GROUP BY product_id")
    List<Integer> searchByOrderIdProductId(@Param("id") Integer id);
    @Query(nativeQuery = true,
            value = "SELECT sum(quantity) " +
                    "from orderdetail " +
                    "where order_id = :id " +
                    "GROUP BY product_id")
    List<Integer> searchByOrderIdQuantity(@Param("id") Integer id);
}
