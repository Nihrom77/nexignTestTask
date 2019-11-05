package com.example.phonesubscriber.domain;

import com.example.phonesubscriber.util.Constants;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Таблица абонентов.
 */
@Entity
public class Subscriber {


    public Subscriber(String name, String lastName, String msisdn, int balance) {
        setName(name);
        setLastName(lastName);
        setBalance(balance);
        if (balance > 0) {
            setStatus(Constants.ACTIVE);
        } else {
            setStatus(Constants.BLOCKED);
        }
        setMsisdn(msisdn);
    }

    public Subscriber() {

    }

    private String name;

    private String lastName;


    //Номер абонента
    @Id
    private String msisdn;

    private int balance;

    //ACTIVE - активен. BLOCKED - заблокирован
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
}
