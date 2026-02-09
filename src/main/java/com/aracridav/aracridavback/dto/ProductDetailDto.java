package com.aracridav.aracridavback.dto;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class ProductDetailDto {
    private Long id;
    private String title;
    private String description;
    private Float price;
    private Integer inStock;
    private String slug;
    private String gender;
    private List<String> tags;
    private List<String> images;
    private List<String> sizes;

    public ProductDetailDto(Long id, String title, String description, Float price,
                            Integer inStock, String slug, String gender,
                            String tagsString, String imagesString, String sizesString) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.inStock = inStock;
        this.slug = slug;
        this.gender = gender;
        this.tags = split(tagsString);
        this.images = split(imagesString);
        this.sizes = split(sizesString);
        
    }

    private List<String> split(String str) {
        if (str == null || str.isBlank()) return List.of();
        return Arrays.asList(str.split(","));
    }
}
