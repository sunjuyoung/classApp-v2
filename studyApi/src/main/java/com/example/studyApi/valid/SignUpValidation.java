package com.example.studyApi.valid;

import com.example.studyApi.dto.SignUpDTO;
import com.example.studyApi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpValidation implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        SignUpDTO signUpDTO = (SignUpDTO) object;
        if(accountRepository.existsByNickname(signUpDTO.getNickname())){
            errors.rejectValue("nickname","invalid.nickname",new Object[]{signUpDTO.getNickname()},"이미 사용중인 닉네임 입니다.");
        }
        if(accountRepository.existsByEmail(signUpDTO.getEmail())){
            errors.rejectValue("email","invalid.email",new Object[]{signUpDTO.getEmail()},"이미 사용중인 이메일 입니다.");
        }
    }
}
