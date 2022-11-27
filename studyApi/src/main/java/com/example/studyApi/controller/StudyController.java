package com.example.studyApi.controller;

import com.example.studyApi.domain.Tag;
import com.example.studyApi.dto.StudyDTO;
import com.example.studyApi.dto.TagDTO;
import com.example.studyApi.service.StudyService;
import com.example.studyApi.valid.StudyValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class StudyController {

    private final StudyService studyService;
    private final StudyValidation studyValidation;
    private final ModelMapper modelMapper;

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
        StudyDTO studyDTO = studyService.getStudy(path);
        return ResponseEntity.ok().body(studyDTO);
    }

    @PostMapping(value = "/study/tag/{path}")
    public ResponseEntity<?> addStudyTag(@PathVariable("path")String path, @RequestBody TagDTO tagDTO){
        studyService.addStudyTag(path,tagDTO);
        return ResponseEntity.ok().body(tagDTO.getTitle());
    }

    @GetMapping(value = "/study/tag/{path}")
    public ResponseEntity<?> getTags(@PathVariable("path")String path){
        List<Tag> tags = studyService.getTags(path);
        List<TagDTO> collect = tags.stream()
                .map(tag -> modelMapper.map(tag, TagDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

}
