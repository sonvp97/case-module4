package com.codegym.model.service.Impl;

import com.codegym.model.dto.CategoryDto;
import com.codegym.model.entity.Category;
import com.codegym.model.repository.CategoryRepository;
import com.codegym.model.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Iterable<CategoryDto> findAll() {
        Iterable<Category> entities = categoryRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), true)
                .map(entity -> modelMapper.map(entity, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        Category entity = categoryRepository.findById(id).orElse(null);
        return Optional.of(modelMapper.map(entity, CategoryDto.class));
    }

    @Override
    public void save(CategoryDto categoryDto) {
        Category role = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(role);

    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }
}
