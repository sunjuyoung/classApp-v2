package com.example.studyApi.controller;

import com.example.studyApi.dto.AccountDTO;
import com.example.studyApi.dto.CurrentUserDTO;
import com.example.studyApi.dto.SignUpDTO;
import com.example.studyApi.service.AccountService;
import com.example.studyApi.util.JWTUtil;
import com.example.studyApi.valid.SignUpValidation;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;
    private final JWTUtil jwtUtil;
    private final SignUpValidation signUpValidation;

    @InitBinder("signUpDTO")
    public void initBinder (WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpValidation);
    }

    @GetMapping("/test/{id}")
    public AccountDTO test(@PathVariable("id")Long id){
        AccountDTO account = accountService.getAccount(id);
        return account;
    }

    @PostMapping(value = "/signUp",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> saveUser(@Valid @RequestBody SignUpDTO signUpDTO, Errors errors){
        log.info("---- ");
        log.info(signUpDTO.getEmail());
        Map<String,String> result = new HashMap<>();
        if(errors.hasErrors()){
            result.put("result",errors.toString());
            return ResponseEntity.ok(result);
        }
        String newUser = accountService.saveUser(signUpDTO);
        result.put("result",newUser);
        return ResponseEntity.ok(result);
    }


}
