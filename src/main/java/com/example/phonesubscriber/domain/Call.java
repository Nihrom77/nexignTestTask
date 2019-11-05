package com.example.phonesubscriber.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Таблица звонков. Аудит звонков по времени и абонентам.
 */
@Entity
public class Call {

    public Call() {

    }

    public Call(LocalDateTime dt, String msisdn) {
        setCallDateTime(dt);
        setMsisdn(msisdn);
    }

    @Id
    @GeneratedValue
    private int id;

    private LocalDateTime callDateTime;
    private String msisdn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCallDateTime() {
        return callDateTime;
    }

    public void setCallDateTime(LocalDateTime callDateTime) {
        this.callDateTime = callDateTime;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }


}
