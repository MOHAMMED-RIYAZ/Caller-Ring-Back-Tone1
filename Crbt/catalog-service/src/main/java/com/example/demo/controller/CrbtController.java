package com.example.demo.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Crbt;
import com.example.demo.repository.CrbtRepository;


@RestController
@RequestMapping("/crbt")
public class CrbtController {

    @Autowired
    private CrbtRepository repository;

    // PUBLIC API
    @GetMapping("/all")
    public List<Crbt> getAllCrbts() {
        return repository.findAll();
    }

    // ADMIN API
    @PostMapping("/add")
    public Crbt addCrbt(@RequestBody Crbt crbt) {
        return repository.save(crbt);
    }

    // ADMIN API
    @PutMapping("/update/{id}")
    public Crbt updateCrbt(@PathVariable Long id,
                           @RequestBody Crbt crbt) {
        crbt.setToneId(id);
        return repository.save(crbt);
    }

    // ADMIN API
    @DeleteMapping("/delete/{id}")
    public String deleteCrbt(@PathVariable Long id) {
        repository.deleteById(id);
        return "CRBT Deleted Successfully";
    }
}