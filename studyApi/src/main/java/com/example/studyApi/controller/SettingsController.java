package com.example.studyApi.controller;

import com.example.studyApi.domain.Tag;
import com.example.studyApi.dto.AccountDTO;
import com.example.studyApi.dto.PasswordDTO;
import com.example.studyApi.dto.ProfileDTO;
import com.example.studyApi.service.AccountService;
import com.example.studyApi.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settings")
public class SettingsController {

    private final AccountService accountService;
    private final SettingsService settingsService;

    @GetMapping(value = "/profile/{nickname}")
    public ResponseEntity<?> getProfile(@PathVariable("nickname")String nickname){
       // AccountDTO accountDTO = accountService.getAccountByNickname(nickname);
        ProfileDTO profileDTO = accountService.getProfileByNickname(nickname);
        return ResponseEntity.ok().body(profileDTO);
    }

    @PostMapping(value = "/profile/{nickname}")
    public ResponseEntity<?> updateProfile(@PathVariable("nickname")String nickname,
                                                    @RequestBody ProfileDTO profileDTO){
        accountService.updateProfile(profileDTO,nickname);
        return ResponseEntity.ok().body(profileDTO);
    }
    @PostMapping(value = "/password/{nickname}")
    public ResponseEntity<?> updatePassword(@PathVariable("nickname")String nickname,
                                            @Valid @RequestBody PasswordDTO passwordDTO, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.ok().body("password not valid");
        }
        accountService.updatePassword(passwordDTO,nickname);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping(value = "/tag")
    public ResponseEntity<?> updatePassword(){
        Set<Tag> tags = settingsService.getTags();
        return ResponseEntity.ok().body("success");
    }

    @PostMapping(value = "/tag/{nickname}")
    public ResponseEntity<?> updateTag(@PathVariable("nickname")String nickname,
                                            @Valid @RequestBody PasswordDTO passwordDTO, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.ok().body("password not valid");
        }
        accountService.updatePassword(passwordDTO,nickname);
        return ResponseEntity.ok().body("success");

    }

}
