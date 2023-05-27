package com.racing.controller;

import com.racing.dao.RaceTypeDao;
import com.racing.model.RaceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RacingController {
    @Autowired
    private RaceTypeDao raceTypeDao;

    @GetMapping("/type/{id}")
    public RaceType getById(@PathVariable Integer id) {
        return raceTypeDao.get(id);
    }

    @GetMapping("/types")
    public List<RaceType> types() {
        return raceTypeDao.all();
    }
}
