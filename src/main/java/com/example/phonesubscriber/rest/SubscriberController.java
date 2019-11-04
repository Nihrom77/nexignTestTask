package com.example.phonesubscriber.rest;

import com.example.phonesubscriber.domain.Prices;
import com.example.phonesubscriber.domain.Subscriber;
import com.example.phonesubscriber.repository.PricesRepository;
import com.example.phonesubscriber.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SubscriberController {

    private final SubscriberRepository subsRepo;

    private final PricesRepository pricesRepo;

    @Autowired
    public SubscriberController(SubscriberRepository subsRepo, PricesRepository pricesRepo) {
        this.pricesRepo = pricesRepo;
        this.subsRepo = subsRepo;
    }

    @GetMapping("/allSubscribers")
    public String allSubscribersPage(Model model) {
        List<Subscriber> subscribers = subsRepo.findAll();
        model.addAttribute("subscribers", subscribers);
        return "allSubscribers";
    }

    @GetMapping("/makecall")
    public String makecallPage(@RequestParam("msisdn") String msisdn, Model model) {
        model.addAttribute("msisdn",msisdn);
        Prices price = pricesRepo.findAll().get(0);
        model.addAttribute("isPresent", false);
        subsRepo.findAll().stream().filter((Subscriber s) -> s.getMsisdn().equals(msisdn)).findAny().ifPresent(
                (Subscriber s) -> {
                    int b = s.getBalance();
                    if (b - price.getCallPrice() >= 0) {
                        s.setBalance(b - price.getCallPrice());
                        s.setStatus(s.getBalance() > 0 ? s.ACTIVE : s.BLOCKED);
                    }
                    model.addAttribute("isPresent", true);
                    model.addAttribute("subscriber", s);
                }
        );
        return "makecall";
    }

}
