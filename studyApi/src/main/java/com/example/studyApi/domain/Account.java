package com.example.studyApi.domain;


import com.example.studyApi.dto.AccountDTO;
import com.example.studyApi.dto.PasswordDTO;
import com.example.studyApi.dto.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "ACCOUNT_SEQ_GENERATOR",
        sequenceName = "ACCOUNT_SEQ",
        initialValue = 1, allocationSize = 1
)
public class Account{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "ACCOUNT_SEQ_GENERATOR")
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean social;

    private LocalDateTime passwordDate;

    @ManyToMany
    @JoinTable(name = "account_tag",
    joinColumns = @JoinColumn(name = "account_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "account_zone",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "zone_id"))
    private Set<Zone> zones = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @Enumerated(EnumType.STRING)
//    @CollectionTable(
//            name = "account_roles",
//            joinColumns = @JoinColumn(name = "account_id"),
//            foreignKey = @ForeignKey(
//                    name = "account_fk",
//                    foreignKeyDefinition = "FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE")
//    )
    private Set<Roles> roles = new HashSet<>();

    private boolean emailVerified;

    private String bio;
    private String url;
    private String location;
    private String occupation;

    private String emailCheckToken;
    private LocalDateTime emailCheckAt;


    private String profileImage;

    private LocalDateTime joinAt;

    //알림
    private boolean notificationOnlyWeb = true;


    public void beginningRole(){
        this.roles.add(Roles.GUEST);
    }

    public void EmailConfirmRole(){
        this.roles.add(Roles.MEMBER);
        this.emailVerified = true;
        this.joinAt = LocalDateTime.now();
        this.passwordDate = LocalDateTime.now();
    }

    public void generateEmailToken(){
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckAt = LocalDateTime.now();
    }

    public void uploadProfileImage(String file){
        this.profileImage = file;
    }


    public void updateProfile(ProfileDTO profileDTO) {
        this.bio = profileDTO.getBio();
        this.url = profileDTO.getUrl();
        this.occupation = profileDTO.getOccupation();
        this.location = profileDTO.getLocation();
    }
    public void changePassword(PasswordDTO passwordDTO){
        this.password = passwordDTO.getPassword();
    }
}
