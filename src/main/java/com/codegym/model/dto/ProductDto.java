package com.codegym.model.dto;

import com.codegym.model.entity.Category;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private MultipartFile image;
    private int quantity;
    private Category category;

    public ProductDto() {
    }

    public ProductDto(String name, Double price, String description, MultipartFile image, int quantity, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
