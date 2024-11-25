package com.example.ProductManagement.dto.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
public class CategoryDto {
    private Integer id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
