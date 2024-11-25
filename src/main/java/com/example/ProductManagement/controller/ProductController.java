package com.example.ProductManagement.controller;

import com.example.ProductManagement.dto.category.CategoryDto;
import com.example.ProductManagement.dto.pageResponse.PageResponseDto;
import com.example.ProductManagement.dto.product.ProductDto;
import com.example.ProductManagement.model.Product;
import com.example.ProductManagement.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public PageResponseDto<ProductDto> getAllProducts(@RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", required = false, defaultValue = "7") Integer pageSize) {
        return productService.findAllProduct(pageNo - 1, pageSize);
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable(name = "id") Integer id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody ProductDto productDto, @PathVariable(name = "id") Integer id) {
        return productService.updateProduct(productDto, id);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable(name = "id") Integer id) {
        return productService.deleteProduct(id);
    }

}
