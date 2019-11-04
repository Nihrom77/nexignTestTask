package com.example.phonesubscriber.repository;

import com.example.phonesubscriber.domain.Prices;
import com.example.phonesubscriber.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricesRepository extends JpaRepository<Prices, Integer> {
}
