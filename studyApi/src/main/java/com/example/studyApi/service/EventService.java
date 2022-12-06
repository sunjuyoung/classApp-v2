package com.example.studyApi.service;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Event;
import com.example.studyApi.domain.Study;
import com.example.studyApi.dto.event.CreateEventDTO;
import com.example.studyApi.dto.event.EventDTO;
import com.example.studyApi.dto.event.EventListDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EventService {

    void createEvent(String nickname, String path, CreateEventDTO createEventDTO);

    default Event newEvent(Study study, Account account,CreateEventDTO createEventDTO){
        Event event = Event.builder()
                .createBy(account)
                .title(createEventDTO.getTitle())
                .description(createEventDTO.getDescription())
                .endDateTime(createEventDTO.getEndDateTime())
                .enrollmentEndTime(createEventDTO.getEnrollmentEndTime())
                .study(study)
                .enrollmentStartTime(createEventDTO.getEnrollmentStartTime())
                .startDateTime(LocalDate.now())
                .limitedNumber(Integer.parseInt(createEventDTO.getLimitedNumber()))
                .build();

        return event;
    }

    Map<String,Object> getEvents(String path);


    default EventListDTO newEventListDTO(Event event){
        EventListDTO eventListDTO = EventListDTO.builder()
                .title(event.getTitle())
                .description(event.getDescription())
                .endDateTime(event.getEndDateTime())
                .enrollmentEndTime(event.getEnrollmentEndTime())
                .enrollmentStartTime(event.getEnrollmentStartTime())
                .startDateTime(event.getStartDateTime())
                .limitedNumber(event.getLimitedNumber())

                .build();
        return eventListDTO;
    }

    default EventDTO newEventDTO(Event event){
        EventDTO eventDTO = EventDTO.builder()
                .title(event.getTitle())
                .description(event.getDescription())
                .endDateTime(event.getEndDateTime())
                .enrollmentEndTime(event.getEnrollmentEndTime())
                .enrollmentStartTime(event.getEnrollmentStartTime())
                .startDateTime(event.getStartDateTime())
                .limitedNumber(event.getLimitedNumber())
                .study(event.getStudy().getTitle())
                .manager(event.getCreateBy().getNickname())
                .enrollments(event.getEnrollments())
                .build();

        return eventDTO;
    }

    EventDTO getEvent(String path, Long id);
}
