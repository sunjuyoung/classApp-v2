package com.example.studyApi.domain;

import lombok.*;

import javax.persistence.*;

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
public class Notification extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "EVENT_SEQ_GENERATOR")
    @Column(name = "notification_id")
    private Long id;

    private String title;

    private String link;

    private String message;

    private boolean checked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;



}
