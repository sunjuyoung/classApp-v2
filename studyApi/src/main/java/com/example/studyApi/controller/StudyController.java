package com.example.studyApi.controller;

import com.example.studyApi.dto.StudyDTO;
import com.example.studyApi.service.StudyService;
import com.example.studyApi.valid.StudyValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class StudyController {

    private final StudyService studyService;
    private final StudyValidation studyValidation;

    @InitBinder("studyDTO")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(studyValidation);
    }

    @PostMapping(value = "/study/{nickname}")
    public ResponseEntity<?> createStudy(@PathVariable("nickname")String nickname,
                                         @Valid @RequestBody StudyDTO studyDTO, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        String studyTitle = studyService.createStudy(studyDTO, nickname);
        return ResponseEntity.ok().body(studyTitle);
    }

    @GetMapping(value = "/study/{path}")
    public ResponseEntity<?> getStudy(@PathVariable("path")String path){

    }

}
