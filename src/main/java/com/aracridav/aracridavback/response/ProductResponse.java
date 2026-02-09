package com.aracridav.aracridavback.response;

public interface ProductResponse {
    Long getId();
    String getTitle();
    String getDescription();
    Float getPrice();
    Integer getInStock();
    String getSlug();
    String getGender(); // debe ser String, no Gender
    String getTags();
    String getImagesUrl();
}
