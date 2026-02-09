package com.aracridav.aracridavback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aracridav.aracridavback.model.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{

}
