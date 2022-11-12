package com.example.studyApi.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseTime{

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true,nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private boolean social;

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

    @Lob
    private String profileImage;


    public void emailTokenGenerate(){
        this.emailCheckToken = UUID.randomUUID().toString();
    }


}
