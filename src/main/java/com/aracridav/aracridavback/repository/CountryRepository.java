package com.aracridav.aracridavback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aracridav.aracridavback.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

}
