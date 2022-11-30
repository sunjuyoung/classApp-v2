package com.example.studyApi.controller;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Study;
import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import com.example.studyApi.dto.StudyDTO;
import com.example.studyApi.dto.TagDTO;
import com.example.studyApi.dto.ZoneDTO;
import com.example.studyApi.dto.study.DescriptionDTO;
import com.example.studyApi.dto.study.ListDTO;
import com.example.studyApi.dto.study.MemberDTO;
import com.example.studyApi.repository.StudyRepository;
import com.example.studyApi.service.StudyService;
import com.example.studyApi.valid.StudyValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    private final StudyRepository studyRepository;

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

    @GetMapping(value = "/study/zone/{path}")
    public ResponseEntity<?> getZones(@PathVariable("path")String path){
        List<Zone> zones = studyService.getZones(path);
        List<String> collect = zones.stream().map(Zone::getLocalNameOfCity).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    @PostMapping(value = "/study/zone/{path}")
    public ResponseEntity<?> addZones(@PathVariable("path")String path , @RequestBody List<ZoneDTO> zoneData){
        studyService.addZone(zoneData,path);
        return ResponseEntity.ok().body(zoneData);
    }

    @GetMapping(value = "/study")
    public ResponseEntity<?> studyList(){
        List<Study> studyAll = studyRepository.findAll();
        List<StudyDTO> collect = studyAll.stream()
                .map(study -> modelMapper.map(study, StudyDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    @GetMapping(value = "/study/list")
    public ResponseEntity<?> studyListBoard(){
        List<Study> studyAll = studyRepository.findAll();
        List<ListDTO> collect = studyAll.stream()
                .map(study -> modelMapper.map(study, ListDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    @GetMapping(value = "/study/desc/{path}")
    public ResponseEntity<DescriptionDTO> studyListBoard(@PathVariable("path")String path){
      DescriptionDTO descriptionDTO =   studyService.studyListBoard(path);
        return ResponseEntity.ok().body(descriptionDTO);
    }

    @GetMapping(value = "/study/members/{path}")
    public ResponseEntity<?> getStudyMembers(@PathVariable("path")String path){
        MemberDTO memberDTO =  studyService.getStudyMembers(path);
        return ResponseEntity.ok().body(memberDTO);
    }

    @PostMapping("/study/publish/{path}/{nickname}")
    public ResponseEntity<String> publishStudy(@PathVariable("path")String path,@PathVariable("nickname")String nickname) {
        if(studyService.publishStudy(path,nickname)){
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.ok().body("failed");
    }
    @PostMapping("/study/close/{path}/{nickname}")
    public ResponseEntity<String> closeStudy(@PathVariable("path")String path,@PathVariable("nickname")String nickname) {
        if(studyService.closeStudy(path,nickname)){
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.ok().body("failed");
    }

    @PostMapping("/study/join/{path}/{nickname}")
    public ResponseEntity<String> joinStudy(@PathVariable("path")String path,@PathVariable("nickname")String nickname) {
        if(studyService.joinStudy(path,nickname)){
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.ok().body("failed");
    }
    @PostMapping("/study/leave/{path}/{nickname}")
    public ResponseEntity<String> leaveStudy(@PathVariable("path")String path,@PathVariable("nickname")String nickname) {
        if(studyService.leaveStudy(path,nickname)){
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.ok().body("failed");
    }


}
