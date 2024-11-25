package com.example.ProductManagement.service;

import com.example.ProductManagement.dto.category.CategoryDto;
import com.example.ProductManagement.dto.pageResponse.PageResponseDto;
import com.example.ProductManagement.dto.product.ProductDto;
import com.example.ProductManagement.model.Category;
import com.example.ProductManagement.model.Product;
import com.example.ProductManagement.repository.CategoryRepository;
import com.example.ProductManagement.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public PageResponseDto<ProductDto> findAllProduct(Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Product> pages = productRepository.findAll(pageRequest);
        List<ProductDto> products = pages.getContent().stream().map(product -> ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .category(CategoryDto.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .description(product.getCategory().getDescription())
                        .createdAt(product.getCategory().getCreatedAt())
                        .updatedAt(product.getCategory().getUpdatedAt())
                        .build())
                .build()).collect(Collectors.toList());

        return PageResponseDto.<ProductDto>builder()
                .data(products)
                .currentPage(pageNo + 1)
                .isLast(pages.isLast())
                .totalItems((int) pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .build();

    }

    public ProductDto addProduct(ProductDto productDto) {

        CategoryDto categoryDto = productDto.getCategory();
        Category dbCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new RuntimeException("The category does not exists"));
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .createdAt(new Date())
                .updatedAt(new Date())
                .category(dbCategory)
                .build();
        CategoryDto dbCategoryDto = CategoryDto.builder()
                .id(dbCategory.getId())
                .name(dbCategory.getName())
                .description(dbCategory.getDescription())
                .createdAt(dbCategory.getCreatedAt())
                .updatedAt(dbCategory.getUpdatedAt())
                .build();

        Product save = productRepository.save(product);
        return ProductDto.builder()
                .id(save.getId())
                .name(save.getName())
                .description(save.getDescription())
                .createdAt(save.getCreatedAt())
                .updatedAt(save.getUpdatedAt())
                .category(dbCategoryDto)
                .build();

    }

    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("The product you are looking for does nor exists"));
        CategoryDto categoryDto = CategoryDto.builder()
                .id(product.getCategory().getId())
                .name(product.getCategory().getName())
                .createdAt(product.getCategory().getCreatedAt())
                .updatedAt(product.getCategory().getUpdatedAt())
                .build();
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .category(categoryDto)
                .build();

    }

    public ProductDto updateProduct(ProductDto productDto,Integer productId) {

        CategoryDto categoryDto = productDto.getCategory();
        Category dbCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new RuntimeException("The category does not exists"));
        Product dbProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("The Product you are looking for does not exists"));
        dbProduct.setName(productDto.getName());
        dbProduct.setUpdatedAt(new Date());
        dbProduct.setDescription(productDto.getDescription());
        dbProduct.setCategory(dbCategory);

        CategoryDto dbCategoryDto = CategoryDto.builder()
                .id(dbCategory.getId())
                .name(dbCategory.getName())
                .description(dbCategory.getDescription())
                .createdAt(dbCategory.getCreatedAt())
                .updatedAt(dbCategory.getUpdatedAt())
                .build();

        Product save = productRepository.save(dbProduct);


        return ProductDto.builder()
                .id(save.getId())
                .name(save.getName())
                .description(save.getDescription())
                .createdAt(save.getCreatedAt())
                .updatedAt(save.getUpdatedAt())
                .category(dbCategoryDto)
                .build();


    }
    public String deleteProduct(Integer id){
        Product dbProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("The Product you are looking for does not exists"));
        productRepository.delete(dbProduct);
        return "Successfully deleted the product";
    }

}
