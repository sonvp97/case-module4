package com.codegym.controller;

import com.codegym.model.entity.User;
import com.codegym.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @GetMapping({ "/","/home"})
    public ModelAndView home() {
        String name =  SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name);
        ModelAndView modelAndView = new ModelAndView();
        if(user.getRole().getName().equals("ROLE_ADMIN")){
            modelAndView = new ModelAndView("/index");
        }else {
            modelAndView = new ModelAndView("redirect:/shop/list");
        }
        return modelAndView;
    }
}
