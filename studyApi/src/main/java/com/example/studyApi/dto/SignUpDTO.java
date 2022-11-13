package com.example.studyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class SignUpDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String nickname;

    @NotBlank
    private String password;
}
