package com.example.studyApi.service;

import com.example.studyApi.dto.AccountDTO;
import com.example.studyApi.dto.CurrentUserDTO;
import com.example.studyApi.dto.SignUpDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {

    AccountDTO getAccount(Long id);

    String saveUser(SignUpDTO signUpDTO);
}
