package com.example.phonesubscriber.rest;

import com.example.phonesubscriber.domain.Call;
import com.example.phonesubscriber.domain.Price;
import com.example.phonesubscriber.domain.Subscriber;
import com.example.phonesubscriber.repository.CallsRepository;
import com.example.phonesubscriber.repository.PricesRepository;
import com.example.phonesubscriber.repository.SubscriberRepository;
import com.example.phonesubscriber.util.Constants;
import com.example.phonesubscriber.util.ReplenishBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class SubscriberController {

    private final SubscriberRepository subsRepo;

    private final PricesRepository pricesRepo;

    private final CallsRepository callsRepository;


    /**
     * Максимальное число звонков в день для одного абонента.
     */
    @Value("${maxCallNumInADay}")
    private int maxCallNumInADay;

    @Autowired
    public SubscriberController(SubscriberRepository subsRepo, PricesRepository pricesRepo, CallsRepository callsRepository) {
        this.pricesRepo = pricesRepo;
        this.subsRepo = subsRepo;
        this.callsRepository = callsRepository;
    }

    @GetMapping("/")
    public String allSubscribersPage(Model model) {
        log.debug("Executing allSubscribersPage");
        List<Subscriber> subscribers = subsRepo.findAll();
        model.addAttribute("subscribers", subscribers);
        return "index";
    }


    /**
     * @param msisdn Номер абонента.
     * @return число звонков, совершенных указанным номером.
     */
    private int getCallForToday(String msisdn) {
        Subscriber s = subsRepo.findByMsisdn(msisdn);
        if (s != null) {
            List<Call> calls = callsRepository.findByMsisdnAndCallDateTimeAfter(msisdn, LocalDate.now().atStartOfDay());
            return calls.size();
        } else {
            return 0;
        }
    }

    /**
     * Обработчик совершения звонка.
     *
     * @param msisdn Номер абонента.
     * @param model  Модель MVC.
     * @return Страница с результатом совершения звонка.
     */
    @GetMapping("/makecall")
    public String makecallPage(@RequestParam("msisdn") String msisdn, Model model) {
        log.debug("Executing makecallPage with msisdn = " + msisdn);
        model.addAttribute("msisdn", msisdn);
        Price price = pricesRepo.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0);
        model.addAttribute("result", "Абонент не найден");
        if (getCallForToday(msisdn) >= maxCallNumInADay) {
            model.addAttribute("result", "Превышен лимит звонков (" + maxCallNumInADay + ") в сутки");
        } else {
            Subscriber s = subsRepo.findByMsisdn(msisdn);
            if (s != null) {
                int b = s.getBalance();
                if (b - price.getCallPrice() >= 0) {
                    s.setBalance(b - price.getCallPrice());
                    s.setStatus(s.getBalance() > 0 ? Constants.ACTIVE : Constants.BLOCKED);
                    callsRepository.save(new Call(LocalDateTime.now(), msisdn));
                    model.addAttribute("result", "Звонок выполнен. Звонков за сегодня " + getCallForToday(msisdn));
                } else {
                    model.addAttribute("result", "Не достаточно средств для звонка.");
                }

                subsRepo.save(s);
            } else {
                return subscriberDoesNotExistsError(msisdn, model);
            }

        }

        return "makecall";
    }

    /**
     * Обработчик отправки SMS.
     *
     * @param msisdn Номер абонента.
     * @param model  Модель MVC.
     * @return Страница с результатом отправки SMS.
     */
    @GetMapping("/sendsms")
    public String sendSMSPage(@RequestParam("msisdn") String msisdn, Model model) {
        log.debug("Executing sendSMSPage with msisdn = " + msisdn);
        model.addAttribute("msisdn", msisdn);
        Price price = pricesRepo.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0);
        Subscriber s = subsRepo.findByMsisdn(msisdn);
        if (s != null) {
            int b = s.getBalance();
            if (b - price.getSmsPrice() >= 0) {
                s.setBalance(b - price.getSmsPrice());
                s.setStatus(s.getBalance() > 0 ? Constants.ACTIVE : Constants.BLOCKED);
                model.addAttribute("result", "SMS отправлена");
            } else {
                model.addAttribute("result", "Не достаточно средств для отправки SMS.");
            }

            subsRepo.save(s);
        } else {
            return subscriberDoesNotExistsError(msisdn, model);
        }


        return "sendsms";
    }

    /**
     * Обработчик операции пополнения баланса.
     *
     * @param msisdn Номер абонента.
     * @param model  Модель MVC.
     * @return Возвращает страницу с формой для пополнения баланса.
     */
    @GetMapping("/replenishBalance")
    public String replenishBalancePage(@RequestParam("msisdn") String msisdn, Model model) {
        log.debug("Executing replenishBalancePage with msisdn = " + msisdn);
        model.addAttribute("msisdn", msisdn);
        Subscriber s = subsRepo.findByMsisdn(msisdn);
        if (s != null) {
            ReplenishBalance b = new ReplenishBalance();
            b.setMsisdn(msisdn);
            model.addAttribute("repleinesh", b);

        } else {
            return subscriberDoesNotExistsError(msisdn, model);
        }

        return "replenishBalanceForm";
    }

    /**
     * Обработчик операции отправки формы на странице пополнения баланса.
     *
     * @param balance Объек, содержащий поля формы.
     * @param model   Модель MVC.
     * @return Страница с результатом операции пополнения баланса.
     */
    @PostMapping("/replenishBalance")
    public String replenishBalanceSubmit(@ModelAttribute ReplenishBalance balance, Model model) {
        log.debug("Executing replenishBalanceSubmit with msisdn = " + balance.getMsisdn());
        model.addAttribute("msisdn", balance.getMsisdn());
        Subscriber s = subsRepo.findByMsisdn(balance.getMsisdn());
        if (s != null) {
            if (balance.getAmount() > 0) {
                s.setBalance(s.getBalance() + balance.getAmount());
            } else {
                return negativeAmountError(balance.getMsisdn(), model);
            }
            subsRepo.save(s);
            model.addAttribute("result", "Баланс успешно пополнен. Текущий баланс = " + s.getBalance());
        } else {
            return subscriberDoesNotExistsError(balance.getMsisdn(), model);
        }


        return "replenishBalanceResult";
    }

    private String subscriberDoesNotExistsError(String msisdn, Model model) {
        model.addAttribute("error", "Абонент с номером " + msisdn + " не существует.");
        return "error";
    }

    private String negativeAmountError(String msisdn, Model model) {
        model.addAttribute("error", "Нельзя пополнить баланс номера (" + msisdn + ") на отрицательную сумму.");
        return "error";
    }

}
