package com.example.studyApi.dto;

import com.example.studyApi.domain.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO  {

    private String bio;
    private String url;
    private String occupation;
    private String location;

    private String profileImage;

    protected ProfileDTO(){}

    public ProfileDTO(Account account){
        this.bio = account.getBio();
        this.url = account.getUrl();
        this.occupation = account.getOccupation();
        this.location = account.getLocation();
        this.profileImage = account.getProfileImage();
    }

}
