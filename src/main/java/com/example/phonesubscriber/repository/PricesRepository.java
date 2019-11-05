package com.example.phonesubscriber.repository;

import com.example.phonesubscriber.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricesRepository extends JpaRepository<Price, Integer> {
}
