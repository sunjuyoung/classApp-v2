package com.example.studyApi.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "STUDY_SEQ_GENERATOR",
        sequenceName = "STUDY_SEQ",
        initialValue = 1, allocationSize = 1
)
public class Study {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "STUDY_SEQ_GENERATOR")
    @Column(name = "study_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account manager;

    @ManyToMany
    private Set<Account> members = new HashSet<>();

    @Column(unique = true)
    private String path;

    private String title;

    private String shortDescription;


    @Lob
    private String fullDescription;

    @Lob
    private String image;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    private Set<Zone> zones = new HashSet<>();


    public void createStudy(Account account){
        this.manager = account;
        this.members.add(account);
    }
}
