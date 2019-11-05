package com.example.phonesubscriber.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Таблица стоимости звонков и SMS.
 */
@Entity
public class Price {
    @Id
    @GeneratedValue
    private int id;

    public Price(int smsPrice, int callPrice) {
        setCallPrice(callPrice);
        setSmsPrice(smsPrice);
    }

    public Price() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSmsPrice() {
        return smsPrice;
    }

    public void setSmsPrice(int smsPrice) {
        this.smsPrice = smsPrice;
    }

    public int getCallPrice() {
        return callPrice;
    }

    public void setCallPrice(int callPrice) {
        this.callPrice = callPrice;
    }

    private int smsPrice;

    private int callPrice;
}
