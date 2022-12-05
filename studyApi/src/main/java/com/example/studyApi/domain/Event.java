package com.example.studyApi.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
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

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account createBy;

    private String description;

    private LocalDate enrollmentEndTime;
    private LocalDate enrollmentStartTime;
    private LocalDate startDateTime;
    private LocalDate endDateTime;

    private int limitedNumber;

    @OneToMany(mappedBy = "event")
    private List<Enrollment> enrollments = new ArrayList<>();







}
