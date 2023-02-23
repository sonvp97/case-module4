package com.codegym.model.service;

import com.codegym.model.dto.CategoryDto;
import com.codegym.model.dto.ProductDto;
import com.codegym.model.entity.Category;
import com.codegym.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends GeneralService<ProductDto> {
    Iterable<ProductDto> findAllByCategory(CategoryDto categoryDto);
    Iterable<ProductDto> findAll();
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByFullNameContaining(String fullName, Pageable pageable);
    Page<Product> findProductByPrice(Pageable pageable);

}
