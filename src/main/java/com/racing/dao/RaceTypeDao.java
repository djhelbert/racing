package com.racing.dao;

import com.racing.model.RaceType;

import java.util.List;

public interface RaceTypeDao {
    List<RaceType> all();
    RaceType get(Integer id);
}
