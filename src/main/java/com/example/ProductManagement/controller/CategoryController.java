package com.example.ProductManagement.controller;

import com.example.ProductManagement.dto.category.CategoryDto;
import com.example.ProductManagement.dto.pageResponse.PageResponseDto;
import com.example.ProductManagement.model.Category;
import com.example.ProductManagement.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public PageResponseDto<CategoryDto> getAllCategories(

            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "7") Integer pageSize) {

        return categoryService.findAllCategory(pageNo - 1, pageSize);
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto){

        return categoryService.addCategory(categoryDto);
    }
    @GetMapping("/{id}")
    public CategoryDto findCategoryById(@PathVariable(name = "id") Integer id){
        return categoryService.findCategoryById(id);
    }
    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable(name = "id") Integer id, @RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(id,categoryDto);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable(name = "id")Integer id){
       return categoryService.deleteCategory(id);
    }

}
