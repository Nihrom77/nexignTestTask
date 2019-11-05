package com.example.phonesubscriber.util;

/**
 * Вспомогательный класс для передачи значений полей из формы пополнения баланса.
 */
public class ReplenishBalance {

    private int amount;
   private String msisdn;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

}
