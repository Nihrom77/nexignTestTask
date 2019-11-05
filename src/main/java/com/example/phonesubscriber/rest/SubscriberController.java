package com.example.phonesubscriber.rest;

import com.example.phonesubscriber.domain.Constants;
import com.example.phonesubscriber.domain.Prices;
import com.example.phonesubscriber.domain.ReplenishBalance;
import com.example.phonesubscriber.domain.Subscriber;
import com.example.phonesubscriber.repository.PricesRepository;
import com.example.phonesubscriber.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SubscriberController {

    private final SubscriberRepository subsRepo;

    private final PricesRepository pricesRepo;

    @Value("${callNumInADay}")
    private int callNumInADay;

    @Autowired
    public SubscriberController(SubscriberRepository subsRepo, PricesRepository pricesRepo) {
        this.pricesRepo = pricesRepo;
        this.subsRepo = subsRepo;
    }

    @GetMapping("/")
    public String allSubscribersPage(Model model) {
        List<Subscriber> subscribers = subsRepo.findAll();
        model.addAttribute("subscribers", subscribers);
        return "index";
    }


    @GetMapping("/makecall")
    public String makecallPage(@RequestParam("msisdn") String msisdn, Model model) {

        model.addAttribute("msisdn", msisdn);
        Prices price = pricesRepo.findAll().get(0);
        model.addAttribute("result", "Абонент не найден");
        subsRepo.findAll().stream().filter((Subscriber s) -> s.getMsisdn().equals(msisdn)).findAny().ifPresent(
                (Subscriber s) -> {
                    int b = s.getBalance();
                    if (b - price.getCallPrice() >= 0) {
                        s.setBalance(b - price.getCallPrice());
                        s.setStatus(s.getBalance() > 0 ? Constants.ACTIVE : Constants.BLOCKED);
                        model.addAttribute("result", "Звонок выполнен");
                    } else {
                        model.addAttribute("result", "Не достаточно средств для звонка.");
                    }

                    subsRepo.save(s);
                }
        );

        return "makecall";
    }

    @GetMapping("/sendsms")
    public String sendSMSPage(@RequestParam("msisdn") String msisdn, Model model) {
        model.addAttribute("msisdn", msisdn);
        Prices price = pricesRepo.findAll().get(0);
        model.addAttribute("result", "Абонент не найден");
        subsRepo.findAll().stream().filter((Subscriber s) -> s.getMsisdn().equals(msisdn)).findAny().ifPresent(
                (Subscriber s) -> {
                    int b = s.getBalance();
                    if (b - price.getSmsPrice() >= 0) {
                        s.setBalance(b - price.getSmsPrice());
                        s.setStatus(s.getBalance() > 0 ? Constants.ACTIVE : Constants.BLOCKED);
                        model.addAttribute("result", "SMS отправлена");
                    } else {
                        model.addAttribute("result", "Не достаточно средств для отправки SMS.");
                    }

                    subsRepo.save(s);
                }
        );

        return "sendsms";
    }

    @GetMapping("/replenishBalance")
    public String replenishBalancePage(@RequestParam("msisdn") String msisdn, Model model) {
        model.addAttribute("msisdn", msisdn);
        subsRepo.findAll().stream().filter((Subscriber s) -> s.getMsisdn().equals(msisdn)).findAny().ifPresent(
                (Subscriber s) -> {
                    ReplenishBalance b = new ReplenishBalance();
                    b.setMsisdn(msisdn);
                    model.addAttribute("repleinesh", b);
                }
        );

        return "replenishBalanceForm";
    }

    @PostMapping("/replenishBalance")
    public String replenishBalanceSubmit(@ModelAttribute ReplenishBalance balance, Model model) {
        model.addAttribute("result", "Абонент не найден");
        model.addAttribute("msisdn", balance.getMsisdn());
        subsRepo.findAll().stream().filter((Subscriber s) -> s.getMsisdn().equals(balance.getMsisdn())).findAny().ifPresent(
                (Subscriber s) -> {
                    if (balance.getAmount() > 0) {
                        s.setBalance(s.getBalance() + balance.getAmount());
                    }
                    subsRepo.save(s);
                    model.addAttribute("result", "Баланс успешно пополнен. Текущий баланс = " + s.getBalance());
                }
        );
        return "replenishBalanceResult";
    }

}
