package com.example.studyApi.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@NamedEntityGraph(name = "Study.withAll", attributeNodes = {
        @NamedAttributeNode("tags"),
        @NamedAttributeNode("zones"),
        @NamedAttributeNode("manager"),
        @NamedAttributeNode("members"),
})
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
    @JoinTable(name = "study_tag",
            joinColumns = @JoinColumn(name = "study_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "study_zone",
            joinColumns = @JoinColumn(name = "study_id"),
            inverseJoinColumns = @JoinColumn(name = "zone_id"))
    private Set<Zone> zones = new HashSet<>();


    private LocalDateTime publishedDateTime;
    private LocalDateTime closedDateTime;
    private LocalDateTime recruitedUpdateDateTime;

    private boolean recruiting;
    private boolean published;
    private boolean closed;
    private boolean useBanner;


    public void createStudy(Account account){
        this.manager = account;
        this.members.add(account);
    }

    public boolean studyRemovable(){
        return !this.published;
    }

    public void publishStudy(){
        this.published = true;
        this.publishedDateTime = LocalDateTime.now();
    }
    public void closeStudy(){
        this.published = false;
    }
}
