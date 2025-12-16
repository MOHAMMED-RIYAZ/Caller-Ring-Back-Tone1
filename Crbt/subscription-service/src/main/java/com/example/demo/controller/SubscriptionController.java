package com.example.demo.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Subscription;
import com.example.demo.repository.SubscriptionRepository;


@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository repository;

    // ACTIVATE CRBT
    @PostMapping("/activate/{toneId}")
    public String activateCrbt(@PathVariable Long toneId,
                               @RequestHeader("X-USER-ID") Long userId) {

        Subscription subscription = new Subscription();
        subscription.setUserId(userId);
        subscription.setToneId(toneId);
        subscription.setActivatedOn(LocalDate.now());

        repository.save(subscription);
        return "CRBT Activated Successfully";
    }

    // VIEW USER CRBTs
    @GetMapping("/my-tones")
    public List<Subscription> getMyCrbts(
            @RequestHeader("X-USER-ID") Long userId) {

        return repository.findByUserId(userId);
    }

    // DEACTIVATE CRBT
    @DeleteMapping("/deactivate/{toneId}")
    public String deactivateCrbt(@PathVariable Long toneId,
                                 @RequestHeader("X-USER-ID") Long userId) {

        List<Subscription> subs = repository.findByUserId(userId);
        subs.stream()
            .filter(s -> s.getToneId().equals(toneId))
            .forEach(repository::delete);

        return "CRBT Deactivated Successfully";
    }
}