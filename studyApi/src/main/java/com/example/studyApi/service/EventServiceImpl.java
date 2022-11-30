package com.example.studyApi.service;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Event;
import com.example.studyApi.domain.Study;
import com.example.studyApi.dto.event.CreateEventDTO;
import com.example.studyApi.repository.AccountRepository;
import com.example.studyApi.repository.EventRepository;
import com.example.studyApi.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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
        Account account = Account.builder()
                .nickname(nickname)
                .build();
        Event event = Event.builder()
                .manager(account)
                .title(createEventDTO.getTitle())
                .description(createEventDTO.getDescription())
                .endDateTime(createEventDTO.getEndDateTime())
                .enrollmentEndTime(createEventDTO.getEnrollmentEndTime())
                .study(study)
                .enrollmentStartTime(createEventDTO.getEnrollmentStartTime())
                .startDateTime(LocalDateTime.now())
                .limitedNumber(createEventDTO.getLimitedNumber())
                .build();
        eventRepository.save(event);
    }
}
