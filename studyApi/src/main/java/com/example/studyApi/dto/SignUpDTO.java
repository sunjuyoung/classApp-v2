package com.example.studyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpDTO {

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9]{3,20}$",message = "3~20자의 영문 소문자,숫자를 입력해주세요, 특수 기호는 불가능합니다.")
    @NotBlank
    private String nickname;

    @NotBlank
    private String password;
}
