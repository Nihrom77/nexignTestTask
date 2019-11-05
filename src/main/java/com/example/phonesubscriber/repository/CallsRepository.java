package com.example.phonesubscriber.repository;

import com.example.phonesubscriber.domain.Call;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CallsRepository extends JpaRepository<Call, Integer> {
    List<Call> findByMsisdnAndCallDateTimeAfter(final String msisdn, final LocalDateTime dt);
}
