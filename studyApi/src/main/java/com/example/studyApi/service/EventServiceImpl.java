package com.example.studyApi.service;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Event;
import com.example.studyApi.domain.Study;
import com.example.studyApi.dto.event.CreateEventDTO;
import com.example.studyApi.dto.event.EventDTO;
import com.example.studyApi.dto.event.EventListDTO;
import com.example.studyApi.repository.AccountRepository;
import com.example.studyApi.repository.EventRepository;
import com.example.studyApi.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final AccountRepository accountRepository;
    private final StudyRepository studyRepository;
    private final ModelMapper modelMapper;


    @Override
    public void createEvent(String nickname, String path, CreateEventDTO createEventDTO) {
        Study study = studyRepository.findByPath(path).get();
        Account account = accountRepository.findByNickname(nickname).get();
        Event event = newEvent(study, account, createEventDTO);
        eventRepository.save(event);
    }

    @Override
    public Map<String,Object> getEvents(String path) {
        Study study = studyRepository.findByPath(path).get();
        List<Event> events = eventRepository.findByStudy(study);
        List<EventListDTO> eventListDTOS = new ArrayList<>();
        List<EventListDTO> oldEventListDTOS = new ArrayList<>();
        if(events != null || events.size() >0){
            events.forEach(event -> {
                if(event.getEndDateTime().isBefore(LocalDate.now())){
                    eventListDTOS.add(newEventListDTO(event));
                }else{
                    oldEventListDTOS.add(newEventListDTO(event));
                }
            });
        }
        Map<String,Object> eventMap = new HashMap<>();
        eventMap.put("newEventList",eventListDTOS);
        eventMap.put("oldEventList",oldEventListDTOS);
        return eventMap;
    }

    @Override
    public EventDTO getEvent(String path, Long id) {
        Event event = eventRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException());
        EventDTO eventDTO = newEventDTO(event);

        return eventDTO;
    }


}
