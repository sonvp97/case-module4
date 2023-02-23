package com.codegym.controller;

import com.codegym.model.dto.CategoryDto;
import com.codegym.model.entity.Category;
import com.codegym.model.entity.Product;
import com.codegym.model.dto.ProductDto;
import com.codegym.model.repository.ProductRepository;
import com.codegym.model.service.CategoryService;
import com.codegym.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Value("${file-upload}")
    private String fileUpload;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<CategoryDto> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new ProductDto());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveProduct(@ModelAttribute ProductDto productDto) {
        MultipartFile multipartFile = productDto.getImage();
        String filename = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productDto.getImage().getBytes(), new File(fileUpload + filename));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        productService.save(productDto);
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new ProductDto());
        modelAndView.addObject("massage","Add New Successfully");
        return modelAndView;
    }
    @GetMapping("/list")
    public ModelAndView listProduct(@RequestParam("search")Optional<String> search, @PageableDefault(value = 5)Pageable pageable){
        Page<Product> products;
        if(search.isPresent()){
            products = productService.findAllByFullNameContaining(search.get(), pageable);
        }else {
            products = productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id){
        ProductDto productDto = productService.findById(id).get();
        return new ModelAndView("/product/edit","product",productDto);
    }
    @PostMapping("/edit")
    public ModelAndView saveEdit(@ModelAttribute("product") ProductDto productDto){
        MultipartFile multipartFile = productDto.getImage();
        String filename = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productDto.getImage().getBytes(), new File(fileUpload + filename));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        productService.save(productDto);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", new ProductDto());
        modelAndView.addObject("massage","Successfully");
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable Long id){
        Product product = productRepository.findById(id).get();
        return new ModelAndView("/product/delete","product",product);
    }
    @PostMapping("/delete")
    public String deleteProduct(@ModelAttribute("product") ProductDto productDto){
       productService.remove(productDto.getId());
       return "redirect:list-product";
    }
}
