package com.codegym.model.service.Impl;

import com.codegym.model.dto.CategoryDto;
import com.codegym.model.dto.ProductDto;
import com.codegym.model.entity.Product;
import com.codegym.model.repository.ProductRepository;
import com.codegym.model.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<ProductDto> findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return Optional.of(modelMapper.map(product,ProductDto.class));
    }

    @Override
    public void save(ProductDto productDto) {
        MultipartFile multipartFile = productDto.getImage();
        String filename = multipartFile.getOriginalFilename();
        Product product = modelMapper.map(productDto,Product.class);
        product.setImage(filename);
        productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }


    @Override
    public Iterable<ProductDto> findAllByCategory(CategoryDto categoryDto) {
        Iterable<Product> entities = productRepository.findAllByCategory(categoryDto);
        return StreamSupport.stream(entities.spliterator(), true)
                .map(entity -> modelMapper.map(entity, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<ProductDto> findAll() {
        Iterable<Product> entities = productRepository.findAll();
        return StreamSupport.stream(entities.spliterator(),true)
                .map(entity -> modelMapper.map(entity,ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllByFullNameContaining(String fullName, Pageable pageable) {
        return productRepository.findAllByNameContaining(fullName, pageable);
    }

    @Override
    public Page<Product> findProductByPrice(Pageable pageable) {
        return productRepository.findProductByPrice(pageable);
    }

}
