package com.example.ProductManagement.dto.product;

import com.example.ProductManagement.dto.category.CategoryDto;
import com.example.ProductManagement.model.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private CategoryDto category;
}
