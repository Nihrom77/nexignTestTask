package com.example.phonesubscriber.rest;

import com.example.phonesubscriber.domain.Subscriber;
import com.example.phonesubscriber.repository.PricesRepository;
import com.example.phonesubscriber.repository.SubscriberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SubscriberRestController {

    private final SubscriberRepository subsRepo;

    @Autowired
    public SubscriberRestController(SubscriberRepository subsRepo, PricesRepository pricesRepo) {
        this.subsRepo = subsRepo;
    }


    /**
     * REST-обработчик операции отображения абонента.
     *
     * @param msisdn Номер абонента.
     * @param model  Модель MVC.
     * @return Объект, преобразуемый в JSON. Абонент, или ошибка.
     */
    @RequestMapping(value = "/subscriber", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Object subscriberPage(@RequestParam("msisdn") String msisdn, Model model) {
        log.debug("Executing subscriberPage with msisdn = "+msisdn);
        Subscriber s = subsRepo.findByMsisdn(msisdn);
        if (s != null) {

            return s;
        } else {
            return "{ \"error\": \"not found\"}";
        }
    }
}
