package com.example.studyApi.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordDTO {

    @NotBlank
    @Length(min = 4,max = 50)
    private String password;

    @NotBlank
    @Length(min = 4,max = 50)
    private String confirmPassword;
}
