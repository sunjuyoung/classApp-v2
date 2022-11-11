package com.example.studyApi.controller;

import com.example.studyApi.dto.AccountDTO;
import com.example.studyApi.dto.CurrentUserDTO;
import com.example.studyApi.service.AccountService;
import com.example.studyApi.util.JWTUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;
    private final JWTUtil jwtUtil;


    @GetMapping("/test/{id}")
    public AccountDTO test(@PathVariable("id")Long id){
        AccountDTO account = accountService.getAccount(id);
        return account;
    }


}
