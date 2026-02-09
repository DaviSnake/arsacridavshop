package com.aracridav.aracridavback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aracridav.aracridavback.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>{

}
