package com.aracridav.aracridavback.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aracridav.aracridavback.model.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long>{  

    @Query(value = """
        SELECT 
            p.id,
            p.title,
            p.description,
            p.price,
            p.in_stock,
            p.slug,
            p.gender,
            GROUP_CONCAT(DISTINCT pt.tags) AS tagsString,
            GROUP_CONCAT(DISTINCT pi2.url) AS imagesString,
            GROUP_CONCAT(DISTINCT ps.sizes) AS sizesString
        FROM product p
        LEFT JOIN product_tags pt ON p.id = pt.product_id
        LEFT JOIN product_image pi2 ON p.id = pi2.product_id
        LEFT JOIN product_sizes ps ON p.id = ps.product_id
        WHERE p.id = :productId
        GROUP BY p.id, p.title, p.description, p.price, p.in_stock, p.slug, p.gender
        """, nativeQuery = true)
    Optional<Object[]> findRawProductDetailById(@Param("productId") Long productId);

    @Query(value = """
        SELECT 
            p.id,
            p.title,
            p.description,
            p.price,
            p.in_stock,
            p.slug,
            p.gender,
            GROUP_CONCAT(DISTINCT pt.tags) AS tagsString,
            GROUP_CONCAT(DISTINCT pi2.url) AS imagesString,
            GROUP_CONCAT(DISTINCT ps.sizes) AS sizesString
        FROM product p
        LEFT JOIN product_tags pt ON p.id = pt.product_id
        LEFT JOIN product_image pi2 ON p.id = pi2.product_id
        LEFT JOIN product_sizes ps ON p.id = ps.product_id
        GROUP BY p.id, p.title, p.description, p.price, p.in_stock, p.slug, p.gender
        """, nativeQuery = true)
    List<Object[]> findRawProductDetails(); 

    @Query(value = """
        SELECT 
            p.id,
            p.title,
            p.description,
            p.price,
            p.in_stock,
            p.slug,
            p.gender,
            GROUP_CONCAT(DISTINCT pt.tags) AS tagsString,
            GROUP_CONCAT(DISTINCT pi2.url) AS imagesString,
            GROUP_CONCAT(DISTINCT ps.sizes) AS sizesString
        FROM product p
        LEFT JOIN product_tags pt ON p.id = pt.product_id
        LEFT JOIN product_image pi2 ON p.id = pi2.product_id
        LEFT JOIN product_sizes ps ON p.id = ps.product_id
        WHERE p.gender = :gender
        GROUP BY p.id, p.title, p.description, p.price, p.in_stock, p.slug, p.gender
        """, nativeQuery = true)
    List<Object[]> findRawProductDetailByGender(@Param("gender") String gender);

    @Query(value = """
        SELECT 
            p.id,
            p.title,
            p.description,
            p.price,
            p.in_stock,
            p.slug,
            p.gender,
            GROUP_CONCAT(DISTINCT pt.tags) AS tagsString,
            GROUP_CONCAT(DISTINCT pi2.url) AS imagesString,
            GROUP_CONCAT(DISTINCT ps.sizes) AS sizesString
        FROM product p
        LEFT JOIN product_tags pt ON p.id = pt.product_id
        LEFT JOIN product_image pi2 ON p.id = pi2.product_id
        LEFT JOIN product_sizes ps ON p.id = ps.product_id
        WHERE p.title LIKE :title
        GROUP BY p.id, p.title, p.description, p.price, p.in_stock, p.slug, p.gender
        """, nativeQuery = true)
    List<Object[]> findRawProductDetailByTitle(@Param("title") String title);

}
