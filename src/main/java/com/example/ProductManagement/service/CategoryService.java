package com.example.ProductManagement.service;

import com.example.ProductManagement.dto.category.CategoryDto;
import com.example.ProductManagement.dto.pageResponse.PageResponseDto;
import com.example.ProductManagement.model.Category;
import com.example.ProductManagement.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public PageResponseDto<CategoryDto> findAllCategory(Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Category> pages = categoryRepository.findAll(pageRequest);
        List<CategoryDto> categories = pages.getContent().stream().map((category -> CategoryDto.builder().id(category.getId()).name(category.getName()).description(category.getDescription())
                .createdAt(category.getCreatedAt()).updatedAt(category.getUpdatedAt()).build())).collect(Collectors.toList());

        return PageResponseDto.<CategoryDto>builder()
                .data(categories)
                .currentPage(pageNo + 1)
                .isLast(pages.isLast())
                .totalItems((int) pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .build();

    }

    public CategoryDto addCategory(CategoryDto categoryDto){
        Category category = Category.builder()
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        Category save = categoryRepository.save(category);
        return CategoryDto.builder()
                .id(save.getId())
                .name(save.getName())
                .description(save.getDescription())
                .createdAt(save.getCreatedAt())
                .updatedAt(save.getUpdatedAt())
                .build();

    }

    public CategoryDto findCategoryById(Integer id){
        Category dbCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("The category you are looking for does not exists"));
        return CategoryDto.builder()
                .id(dbCategory.getId())
                .name(dbCategory.getName())
                .description(dbCategory.getDescription())
                .createdAt(dbCategory.getCreatedAt())
                .updatedAt(dbCategory.getUpdatedAt())
                .build();
    }

    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto){
        Category dbCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("The category you are looking for does not exists"));
        dbCategory.setUpdatedAt(new Date());
        dbCategory.setName(categoryDto.getName());
        dbCategory.setDescription(categoryDto.getDescription());

        Category save = categoryRepository.save(dbCategory);
        return CategoryDto.builder()
                .id(save.getId())
                .name(save.getName())
                .description(save.getDescription())
                .createdAt(save.getCreatedAt())
                .updatedAt(save.getUpdatedAt())
                .build();
    }

    public String deleteCategory(Integer id){
        Category dbCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("The category you are looking for does not exists"));

        categoryRepository.delete(dbCategory);

        return "successfully deleted the  category";
    }

}
