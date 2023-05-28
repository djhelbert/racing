package com.racing.dao;

import com.racing.model.Organizer;

import java.util.List;

public interface OrganizerDao {
    List<Organizer> getByTypeOrState(String type, String state);

    Organizer get(Integer id);
}
