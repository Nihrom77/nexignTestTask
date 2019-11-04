package com.example.phonesubscriber.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Prices {
    @Id
    @GeneratedValue
    private int id;

    public Prices(int smsPrice, int callPrice){
        setCallPrice(callPrice);
        setSmsPrice(smsPrice);
    }

    public Prices(){

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
