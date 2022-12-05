package com.example.studyApi.controller;

import com.example.studyApi.domain.Event;
import com.example.studyApi.dto.StudyDTO;
import com.example.studyApi.dto.event.CreateEventDTO;
import com.example.studyApi.dto.event.EventDTO;
import com.example.studyApi.dto.event.EventListDTO;
import com.example.studyApi.service.EventService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/event")
public class EventController {

    private final EventService eventService;


    @PostMapping(value = "/create/{path}/{nickname}")
    public ResponseEntity<?> createEvent(@PathVariable("nickname")String nickname, @PathVariable("path")String path,
                                         @Valid @RequestBody CreateEventDTO createEventDTO, Errors errors){
        eventService.createEvent(nickname,path,createEventDTO);
        return ResponseEntity.ok().body("success");
    }


    @GetMapping(value = "/{path}")
    public ResponseEntity<List<EventListDTO>> getEvents(@PathVariable("path")String path){
        List<EventListDTO> events = eventService.getEvents(path);
        return ResponseEntity.ok().body(events);
    }


    @GetMapping(value = "/{path}/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable("path")String path,@PathVariable("id")Long id){
        EventDTO event = eventService.getEvent(path, id);
        return ResponseEntity.ok().body(event);
    }


}
