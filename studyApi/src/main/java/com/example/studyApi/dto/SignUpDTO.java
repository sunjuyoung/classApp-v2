package com.example.studyApi.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class SignUpDTO {

    private String email;

    private String nickname;

    private String password;
}
