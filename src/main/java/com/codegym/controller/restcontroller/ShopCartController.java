//package com.codegym.controller.restcontroller;
//
//import com.codegym.model.entity.Order;
//import com.codegym.model.entity.User;
//import com.codegym.model.repository.UserRepository;
//import com.codegym.model.service.OrderDetailService;
//import com.codegym.model.service.OrderService;
//import com.codegym.model.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.HashMap;
//import java.util.List;
//
//@RestController
//@RequestMapping("/cart")
//public class ShopCartController {
//    @Autowired
//    private OrderDetailService orderDetailService;
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private UserRepository userRepository;
//    @GetMapping("/list")
//    public ModelAndView showCart(){
//        String name =  SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByUsername(name);
//        Order order = orderService.findByCustomerId(Math.toIntExact(user.getId()));
//        List<Integer> product = orderDetailService.searchByOrderIdProduct(order.getId());
//        List<Integer> quantity = orderDetailService.searchByOrderIdQuantity(order.getId());
//        ModelAndView modelAndView = new ModelAndView("/shop/cart");
//        modelAndView.addObject("products",product);
//        modelAndView.addObject("quantities",quantity);
//        modelAndView.addObject("user",user);
//        return modelAndView;
//    }
//}
