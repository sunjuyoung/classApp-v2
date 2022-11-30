package com.example.studyApi.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "STUDY_SEQ_GENERATOR",
        sequenceName = "STUDY_SEQ",
        initialValue = 1, allocationSize = 1
)
public class Enrollment extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "ENROLLMENT_SEQ_GENERATOR")
    @Column(name = "enrollment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @ManyToOne
    private Account account;

    private boolean accept;

    private boolean attend;





}
