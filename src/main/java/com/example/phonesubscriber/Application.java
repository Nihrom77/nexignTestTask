package com.example.phonesubscriber;

import com.example.phonesubscriber.domain.Prices;
import com.example.phonesubscriber.domain.Subscriber;
import com.example.phonesubscriber.repository.PricesRepository;
import com.example.phonesubscriber.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private SubscriberRepository repository;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private PricesRepository pricesRepository;


    @PostConstruct
    public void init() {
        repository.save(new Subscriber("Roman", "K.","890955533535",10));
        repository.save(new Subscriber("Vladimir", "I.","8909555354315",15));

        pricesRepository.save(new Prices(1, 4));
    }

}
