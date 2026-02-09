package com.aracridav.aracridavback.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    Long id;
    String description;
    List<String> images;
    String inStock;
    String price;
    List<String> sizes;
    String slug;
    String type;
    List<String> tags;
    String title;
    String gender;
}
