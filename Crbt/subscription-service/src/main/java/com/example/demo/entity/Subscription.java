package com.example.demo.entity;


import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subId;

    private Long userId;
    private Long toneId;
    private LocalDate activatedOn;

    // getters & setters
    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getToneId() {
        return toneId;
    }

    public void setToneId(Long toneId) {
        this.toneId = toneId;
    }

    public LocalDate getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(LocalDate activatedOn) {
        this.activatedOn = activatedOn;
    }
}
