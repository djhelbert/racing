package com.racing.controller;

import com.racing.dao.OrganizerDao;
import com.racing.dao.RaceTypeDao;
import com.racing.model.Organizer;
import com.racing.model.RaceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RacingController {
    @Autowired
    private RaceTypeDao raceTypeDao;
    @Autowired
    private OrganizerDao organizerDao;

    @GetMapping("/type/{id}")
    public RaceType getRaceTypeById(@PathVariable Integer id) {
        return raceTypeDao.get(id);
    }

    @GetMapping("/organizer/{id}")
    public Organizer getOrganizerById(@PathVariable Integer id) {
        return organizerDao.get(id);
    }

    @GetMapping("/organizer/{type}/{state}")
    public List<Organizer> getOrganizerByTypeState(@PathVariable String type, @PathVariable String state) {
        return organizerDao.getByTypeOrState(type, state);
    }

    @GetMapping("/types")
    public List<RaceType> types() {
        return raceTypeDao.all();
    }
}
