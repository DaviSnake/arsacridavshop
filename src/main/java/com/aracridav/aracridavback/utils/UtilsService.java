package com.aracridav.aracridavback.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aracridav.aracridavback.dto.CategoryDto;
import com.aracridav.aracridavback.dto.CountryDto;
import com.aracridav.aracridavback.dto.ProductDto;
import com.aracridav.aracridavback.dto.UserDto;
import com.aracridav.aracridavback.model.Category;
import com.aracridav.aracridavback.model.Country;
import com.aracridav.aracridavback.model.Gender;
import com.aracridav.aracridavback.model.Product;
import com.aracridav.aracridavback.model.ProductImage;
import com.aracridav.aracridavback.model.Size;
import com.aracridav.aracridavback.model.User;
import com.aracridav.aracridavback.repository.CategoryRepository;
import com.aracridav.aracridavback.repository.CountryRepository;
import com.aracridav.aracridavback.repository.OrderAddressRepository;
import com.aracridav.aracridavback.repository.OrderItemRepository;
import com.aracridav.aracridavback.repository.OrderRepository;
import com.aracridav.aracridavback.repository.ProductImageRepository;
import com.aracridav.aracridavback.repository.ProductRepository;
import com.aracridav.aracridavback.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilsService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CountryRepository countryRepository;
    private final CategoryRepository categoryRepository;
    private final OrderAddressRepository orderAddressRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public void deleteData() throws IOException{
        
        orderAddressRepository.deleteAll();
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();

        userRepository.deleteAll();
        countryRepository.deleteAll();

        productImageRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Transactional
    public void loadeData() throws IOException{

        InputStream inputStreamUser = getClass().getResourceAsStream("/static/user.json");
        if (inputStreamUser == null) throw new FileNotFoundException("user.json not found");

        List<UserDto> dtoListUser = Arrays.asList(objectMapper.readValue(inputStreamUser, UserDto[].class));
        
        InputStream inputStreamCountry = getClass().getResourceAsStream("/static/country.json");
        if (inputStreamCountry == null) throw new FileNotFoundException("country.json not found");

        List<CountryDto> dtoListCountry = Arrays.asList(objectMapper.readValue(inputStreamCountry, CountryDto[].class));
        
        InputStream inputStreamCategory = getClass().getResourceAsStream("/static/categories.json");
        if (inputStreamCategory == null) throw new FileNotFoundException("categories.json not found");

        List<CategoryDto> dtoListCategory = Arrays.asList(objectMapper.readValue(inputStreamCategory, CategoryDto[].class));
        
        InputStream inputStreamProduct = getClass().getResourceAsStream("/static/products.json");
        if (inputStreamProduct == null) throw new FileNotFoundException("products.json not found");

        List<ProductDto> dtoListProduct = Arrays.asList(objectMapper.readValue(inputStreamProduct, ProductDto[].class));

        for (UserDto userTemp : dtoListUser) {
            User user = User.builder()
            .userName(userTemp.getUserName())
            .fullName(userTemp.getFullName())
            .password(passwordEncoder.encode(userTemp.getPassword()))
            .rol(userTemp.getRol())
            .build();
            
            userRepository.save(user);  
        } 

        for (CountryDto countryTemp : dtoListCountry) {
            Country country = Country.builder()
            .id(countryTemp.getId())
            .name(countryTemp.getName())
            .build();
            
            countryRepository.save(country);            
        }

        for (CategoryDto categoryTemp : dtoListCategory) {
            Category category = Category.builder()
            .name(categoryTemp.getName())
            .build();
            
            categoryRepository.save(category);            
        }

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

}
