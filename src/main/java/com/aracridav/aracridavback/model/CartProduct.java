package com.aracridav.aracridavback.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class CartProduct {
    @Id
    private String id;
    private String slug;
    private String title;
    private Integer price;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Size size;

    private String image;
    private Boolean status;
}
