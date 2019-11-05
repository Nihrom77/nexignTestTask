package com.example.phonesubscriber.repository;

import com.example.phonesubscriber.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    Subscriber findByMsisdn(String msisdn);
}
