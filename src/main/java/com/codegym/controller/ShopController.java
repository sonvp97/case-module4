package com.codegym.controller;

import com.codegym.model.dto.CategoryDto;
import com.codegym.model.dto.ProductDto;
import com.codegym.model.entity.Order;
import com.codegym.model.entity.OrderDetail;
import com.codegym.model.entity.Product;
import com.codegym.model.entity.User;
import com.codegym.model.repository.OrderDetailRepository;
import com.codegym.model.repository.ProductRepository;
import com.codegym.model.repository.UserRepository;
import com.codegym.model.service.OrderDetailService;
import com.codegym.model.service.OrderService;
import com.codegym.model.service.ProductService;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    private HashMap<Integer, Integer> hashMap = new HashMap<>();


    @GetMapping("/list")
    public ModelAndView listProduct(@RequestParam("search") Optional<String> search, @PageableDefault(value = 8) Pageable pageable) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name);
        Page<Product> products;
        if (search.isPresent()) {
            products = productService.findAllByFullNameContaining(search.get(), pageable);
        } else {
            products = productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/shop/list");
        modelAndView.addObject("products", products);
        modelAndView.addObject("user", user);
        if(user==null){
            return modelAndView;
        }else {
            Order order = orderService.findByCustomerId(Math.toIntExact(user.getId()));
            if (order == null) {
                order = new Order(Math.toIntExact(user.getId()));
            }
            orderService.save(order);
            order = orderService.findByCustomerId(Math.toIntExact(user.getId()));

            List<Integer> product = orderDetailService.searchByOrderIdProduct(order.getId());
            List<Integer> quantity = orderDetailService.searchByOrderIdQuantity(order.getId());
            for (int i = 0; i < product.size(); i++) {
                hashMap.put(product.get(i), quantity.get(i));
            }
        }

        return modelAndView;
    }

    @GetMapping("/add/{id}")
    public String showCart(@PathVariable("id") Integer id) {
        if (hashMap.containsKey(id)) {
            hashMap.put(id, hashMap.get(id) + 1);
        } else {
            hashMap.put(id, 1);
        }
        return "redirect:/shop/list";
    }

    @GetMapping("/cart")
    public ModelAndView showCart() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name);
        ModelAndView modelAndView = new ModelAndView("/shop/cart");
        modelAndView.addObject("user", user);
        HashMap<Product, Integer> product = new HashMap<>();
        Double total = 0.0;
        for (Integer i : hashMap.keySet()) {
            Product product1 = productRepository.findById(Long.valueOf(i)).get();
            product.put(product1, hashMap.get(i));
            total += product1.getPrice() * hashMap.get(i);
        }
        modelAndView.addObject("products", product);
        modelAndView.addObject("total", total);
        return modelAndView;
    }

    @GetMapping("/cart/{id}")
    public String Cart(@PathVariable("id") Integer id, @RequestParam("action") String action) {
        // Your code here
        if (action.equals("'add'")) {
            hashMap.put(id,hashMap.get(id)+1);
        } else {
            if(hashMap.get(id)-1 < 0){
                hashMap.remove(id);
            }else {
                hashMap.put(id,hashMap.get(id)-1);
            }

        }
        return "redirect:/shop/cart";
    }

}
