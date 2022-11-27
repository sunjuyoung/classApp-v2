package com.example.studyApi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "EVENT_SEQ_GENERATOR",
        sequenceName = "STUDY_SEQ",
        initialValue = 1, allocationSize = 1
)
public class Event extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "EVENT_SEQ_GENERATOR")
    @Column(name = "event_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account manager;

    private String description;

    private LocalDateTime enrollmentEndTime;
    private LocalDateTime enrollmentStartTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private int limitedNumber;







}
