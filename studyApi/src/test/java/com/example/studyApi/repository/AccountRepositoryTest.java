package com.example.studyApi.repository;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Roles;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSave(){

        Set<Roles> roles = new HashSet<>();
        roles.add(Roles.ADMIN);

        Account account = Account.builder()
                .nickname("ju")
                .email("syseoz@naver.com")
                .roles(roles)
                .password(passwordEncoder.encode("1234"))
                .build();

        accountRepository.save(account);
    }

}