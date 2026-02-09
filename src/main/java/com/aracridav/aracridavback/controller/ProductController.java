package com.aracridav.aracridavback.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aracridav.aracridavback.dto.ProductDetailDto;
import com.aracridav.aracridavback.dto.ProductDto;
import com.aracridav.aracridavback.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PutMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody List<ProductDto> productDtos)
    {
       productService.addProduct(productDtos);

       return ResponseEntity.ok("Add products");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody List<ProductDto> productDtos)
    {
       productService.updateProduct(productDtos);

       return ResponseEntity.ok("Update products");
    }

    @GetMapping("/allProduct")
    public ResponseEntity<List<ProductDetailDto>> getAllProduct() {

        List<ProductDetailDto> products = productService.getProductDetail();

        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<ProductDetailDto> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "gender/{gender}")
    public ResponseEntity<List<ProductDetailDto>> getProductByGender(@PathVariable String gender) {

        List<ProductDetailDto> products = productService.getProductByGender(gender);

        return ResponseEntity.ok(products);
    }
    
    @GetMapping(value = "title/{title}")
    public ResponseEntity<List<ProductDetailDto>> getProductByTitle(@PathVariable String title) {

        List<ProductDetailDto> products = productService.getProductByTitle(title);

        return ResponseEntity.ok(products);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        boolean eliminado = productService.deleteUserById(id);
        if (eliminado) {
            return ResponseEntity.ok("Delete Product");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
  
}
