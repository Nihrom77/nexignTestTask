package com.example.phonesubscriber.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Subscriber {

    public final String ACTIVE = "ACTIVE";
    public final String BLOCKED = "BLOCKED";

    public Subscriber(String name, String lastName, String msisdn, int balance) {
        setName(name);
        setLastName(lastName);
        setBalance(balance);
        if (balance > 0) {
            setStatus(ACTIVE);
        } else {
            setStatus(BLOCKED);
        }
        setMsisdn(msisdn);
    }

    public Subscriber(){

    }
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String lastName;


    //Номер абонента
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
