package com.example.phonesubscriber.rest;

import com.example.phonesubscriber.domain.Subscriber;
import com.example.phonesubscriber.repository.PricesRepository;
import com.example.phonesubscriber.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SubscriberRestController {

    private final SubscriberRepository subsRepo;
    private final PricesRepository pricesRepo;

    @Autowired
    public SubscriberRestController(SubscriberRepository subsRepo, PricesRepository pricesRepo) {
        this.pricesRepo = pricesRepo;
        this.subsRepo = subsRepo;
    }


    @RequestMapping(value = "/subscriber", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Object subscriberPage(@RequestParam("msisdn") String msisdn, Model model) {
        Optional<Subscriber> o = subsRepo.findAll().stream().filter((Subscriber s) -> s.getMsisdn().equals(msisdn))
                .findAny();

        if (o.isPresent()) {
            return o.get();
        } else {
            return "{ \"error\": \"not found\"}";
        }
    }
}
