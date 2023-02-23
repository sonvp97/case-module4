package com.codegym.controller;

import com.codegym.model.dto.CategoryDto;
import com.codegym.model.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ModelAndView listCategory(){
        Iterable<CategoryDto> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("/category/list","categories",categories);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView createCategory(){
        ModelAndView modelAndView = new ModelAndView("/category/create","category",new CategoryDto());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView saveCategory(@ModelAttribute CategoryDto categoryDto){
        categoryService.save(categoryDto);
        ModelAndView modelAndView = new ModelAndView("/category/create","category",new CategoryDto());
        modelAndView.addObject("message","Add New Success");
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView("/category/edit","category",categoryService.findById(id).get());
        return modelAndView;
    }
    @PostMapping("/edit")
    public ModelAndView editCategory(@ModelAttribute("category") CategoryDto categoryDto){
        categoryService.save(categoryDto);
        ModelAndView modelAndView = new ModelAndView("/category/edit","category",new CategoryDto());
        modelAndView.addObject("message","fix successfully");
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable("id") Long id){
        CategoryDto categoryDto = categoryService.findById(id).get();
        ModelAndView modelAndView = new ModelAndView("/category/delete","category",categoryDto);
        return modelAndView;
    }
    @PostMapping("/delete")
    public String deleteCategory(@ModelAttribute("category") CategoryDto categoryDto){
        categoryService.remove(categoryDto.getId());
        return "redirect:list";
    }

}
