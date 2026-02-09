package com.aracridav.aracridavback.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Country {
    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "country")
    @Builder.Default
    private List<UserAddress> userAddresses = new ArrayList<>();

    @OneToMany(mappedBy = "country")
    @Builder.Default
    private List<OrderAddress> orderAddresses = new ArrayList<>();

}
