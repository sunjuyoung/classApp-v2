package com.example.studyApi.dto;

import com.example.studyApi.domain.Roles;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO{

    private String email;

    private String nickname;


    private boolean social;

    private Set<Roles> roles = new HashSet<>();

    private boolean emailVerified;

    private String bio;
    private String url;
    private String location;
    private String occupation;

    private String profileImage;
}
