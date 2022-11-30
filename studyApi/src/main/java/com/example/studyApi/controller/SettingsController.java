package com.example.studyApi.controller;

import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import com.example.studyApi.dto.*;
import com.example.studyApi.dto.file.ProfileImageDTO;
import com.example.studyApi.repository.ZoneRepository;
import com.example.studyApi.service.AccountService;
import com.example.studyApi.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settings")
public class SettingsController {

    private final AccountService accountService;
    private final SettingsService settingsService;
    private final ZoneRepository zoneRepository;

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

    /**
     *  TAG
     */
    @GetMapping(value = "/tag/{nickname}")
    public ResponseEntity<?> updatePassword(@PathVariable("nickname")String nickname){
        List<String> tags = settingsService.getTags(nickname);
        if(tags == null){
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.ok().body(tags);
    }

    @PostMapping(value = "/tag/{nickname}")
    public ResponseEntity<?> updateTag(@PathVariable("nickname")String nickname, @RequestBody TagDTO tagDTO ){
        Tag tag = settingsService.saveTag(tagDTO);
        if(tag !=null){
            accountService.addTag(nickname,tag);
        }
        return ResponseEntity.ok().body("success");
    }

    @DeleteMapping(value = "/tag/{nickname}/{tagTitle}")
    public ResponseEntity<?> deleteTag(@PathVariable("nickname")String nickname,@PathVariable("tagTitle")String tagTitle ){
         accountService.deleteTag(tagTitle,nickname);
        return ResponseEntity.ok().body("success");
    }

    /** ZONE
     */
    @GetMapping(value = "/zone")
    public ResponseEntity<?> getZones( ){
        List<Zone> all = zoneRepository.findAll();
        List<String> zones = all.stream().map(Zone::getLocalNameOfCity).collect(Collectors.toList());
        return ResponseEntity.ok().body(zones);
    }

    @GetMapping(value = "/zone/{nickname}")
    public ResponseEntity<?> getZone(@PathVariable("nickname")String nickname ){
        List<String> zone = accountService.getZone(nickname);
        return ResponseEntity.ok().body(zone);
    }

    @PostMapping(value = "/zone/{nickname}")
    public ResponseEntity<?> addZone(@PathVariable("nickname")String nickname , @RequestBody List<ZoneDTO> zoneData){
        accountService.addZone(zoneData,nickname);
        return ResponseEntity.ok().body(zoneData);
    }


    @Value("${com.example.studyApi.path}")
    private String uploadPath;

    @PostMapping(value = "/profile/upload/{nickname}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String upload(@PathVariable("nickname")String nickname ,ProfileImageDTO file){
        String path = accountService.uploadProfileImage(nickname,file);
        return path;
    }


}
