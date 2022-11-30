package com.example.studyApi.service;

import com.example.studyApi.dto.event.CreateEventDTO;

public interface EventService {

    void createEvent(String nickname, String path, CreateEventDTO createEventDTO);
}
