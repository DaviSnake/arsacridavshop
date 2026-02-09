package com.aracridav.aracridavback.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aracridav.aracridavback.dto.ProductDetailDto;
import com.aracridav.aracridavback.dto.ProductDto;
import com.aracridav.aracridavback.model.Category;
import com.aracridav.aracridavback.model.Gender;
import com.aracridav.aracridavback.model.Product;
import com.aracridav.aracridavback.model.ProductImage;
import com.aracridav.aracridavback.model.Size;
import com.aracridav.aracridavback.repository.CategoryRepository;
import com.aracridav.aracridavback.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void addProduct(List<ProductDto> dtoListProduct){
        for (ProductDto productTemp : dtoListProduct) {
            String type = productTemp.getType().substring(0, 1).toUpperCase() + productTemp.getType().substring(1);
            String gander = productTemp.getGender().substring(0, 1).toUpperCase() + productTemp.getGender().substring(1);
            Category category = categoryRepository.findByName(type)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            
            // Convertir sizes (strings) a enums
            List<Size> sizeEnums = productTemp.getSizes().stream()
                .map(s -> Size.valueOf(s.toUpperCase()))
                .collect(Collectors.toList());

            Product product = Product.builder()
            .title(productTemp.getTitle())
            .description(productTemp.getDescription())
            .inStock(Integer.valueOf(productTemp.getInStock()))
            .price(Float.valueOf(productTemp.getPrice()))
            .slug(productTemp.getSlug())
            .sizes(sizeEnums)
            .gender(Gender.valueOf(gander))
            .category(category)
            .tags(productTemp.getTags())
            .build();

            // Crear lista de ProductImage
            List<ProductImage> imagenes = productTemp.getImages().stream().map(url -> {
                ProductImage img = new ProductImage();
                img.setUrl(url);
                img.setProduct(product); // relacionar
                return img;
            }).collect(Collectors.toList());

            product.getImages().addAll(imagenes);

            productRepository.save(product);
            
        }
    }
    
    public void updateProduct(List<ProductDto> dtoListProduct){
        for (ProductDto productTemp : dtoListProduct) {
            String type = productTemp.getType().substring(0, 1).toUpperCase() + productTemp.getType().substring(1);
            String gander = productTemp.getGender().substring(0, 1).toUpperCase() + productTemp.getGender().substring(1);
            Category category = categoryRepository.findByName(type)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            
            // Convertir sizes (strings) a enums
            List<Size> sizeEnums = productTemp.getSizes().stream()
                .map(s -> Size.valueOf(s.toUpperCase()))
                .collect(Collectors.toList());

            Product product = Product.builder()
            .id(productTemp.getId())
            .title(productTemp.getTitle())
            .description(productTemp.getDescription())
            .inStock(Integer.valueOf(productTemp.getInStock()))
            .price(Float.valueOf(productTemp.getPrice()))
            .slug(productTemp.getSlug())
            .sizes(sizeEnums)
            .gender(Gender.valueOf(gander))
            .category(category)
            .tags(productTemp.getTags())
            .build();

            // Crear lista de ProductImage
            List<ProductImage> imagenes = productTemp.getImages().stream().map(url -> {
                ProductImage img = new ProductImage();
                img.setUrl(url);
                img.setProduct(product); // relacionar
                return img;
            }).collect(Collectors.toList());

            product.getImages().addAll(imagenes);

            productRepository.save(product);
            
        }
    }

    public Optional<ProductDetailDto> getProductById(Long id) {
        return productRepository.findRawProductDetailById(id)
                .map(this::mapToDto);
    }

    public List<ProductDetailDto> getProductByGender(String gender) {
        List<Object[]> rawData = productRepository.findRawProductDetailByGender(gender);

        return rawData.stream().map(row -> new ProductDetailDto(
            ((Number) row[0]).longValue(),     // id
            (String) row[1],                   // title
            (String) row[2],                   // description
            ((Number) row[3]).floatValue(),    // price
            ((Number) row[4]).intValue(),      // inStock
            (String) row[5],                   // slug
            (String) row[6],                   // gender
            (String) row[7],                   // tagsString
            (String) row[8],                   // imagesString
            (String) row[9]                    // sizesString
        )).toList();
    }

    public List<ProductDetailDto> getProductByTitle(String title) {
        
        
        List<Object[]> rawData = productRepository.findRawProductDetailByTitle("%" + title + "%");

        return rawData.stream().map(row -> new ProductDetailDto(
            ((Number) row[0]).longValue(),     // id
            (String) row[1],                   // title
            (String) row[2],                   // description
            ((Number) row[3]).floatValue(),    // price
            ((Number) row[4]).intValue(),      // inStock
            (String) row[5],                   // slug
            (String) row[6],                   // gender
            (String) row[7],                   // tagsString
            (String) row[8],                   // imagesString
            (String) row[9]                    // sizesString
        )).toList();
    }

    public List<ProductDetailDto> getProductDetail() {
        List<Object[]> rawData = productRepository.findRawProductDetails();

        return rawData.stream().map(row -> new ProductDetailDto(
            ((Number) row[0]).longValue(),     // id
            (String) row[1],                   // title
            (String) row[2],                   // description
            ((Number) row[3]).floatValue(),    // price
            ((Number) row[4]).intValue(),      // inStock
            (String) row[5],                   // slug
            (String) row[6],                   // gender
            (String) row[7],                   // tagsString
            (String) row[8],                   // imagesString
            (String) row[9]                    // sizesString
        )).toList();
    }

    public boolean deleteUserById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProductDetailDto mapToDto(Object[] consulta) {

        Object[] row = (Object[]) consulta[0];

        return new ProductDetailDto(
            ((Number) row[0]).longValue(),    // id
            (String) row[1],                  // title
            (String) row[2],                  // description
            ((Number) row[3]).floatValue(),   // price
            ((Number) row[4]).intValue(),     // in_stock
            (String) row[5],                  // slug
            (String) row[6],                  // gender
            (String) row[7],                  // tags
            (String) row[8],                  // images
            (String) row[9]                   // sizes
        );
    }

}
