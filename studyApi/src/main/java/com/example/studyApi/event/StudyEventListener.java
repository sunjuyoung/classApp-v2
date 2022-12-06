package com.example.studyApi.event;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Notification;
import com.example.studyApi.domain.NotificationType;
import com.example.studyApi.domain.Study;
import com.example.studyApi.repository.*;
import com.example.studyApi.service.AccountServiceImpl;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@Transactional
@RequiredArgsConstructor
public class StudyEventListener {

    private final StudyRepository studyRepository;
    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;

    @EventListener
    public void onApplicationEvent(StudyCreatedEvent event) {
        log.info(" event listener");
        log.info(event.getStudy().getTitle());

        Study study =  studyRepository.findStudyWithTagsAndZonesById(event.getStudy().getId());
        Iterable<Account> accounts = accountRepository
                .findAll(AccountPredicates.findByTagsAndZones(study.getTags(), study.getZones()));

        for (Account account : accounts) {
            Notification notification = Notification.builder()
                    .title(study.getTitle())
                    .account(account)
                    .notificationType(NotificationType.STUDY_CREATED)
                    .link("/api/study/"+study.getPath())
                    .message(study.getShortDescription())
                    .checked(false)
                    .build();
            notificationRepository.save(notification);
        }


    }
}
