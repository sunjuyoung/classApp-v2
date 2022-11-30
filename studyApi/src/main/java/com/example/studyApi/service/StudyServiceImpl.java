package com.example.studyApi.service;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Study;
import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import com.example.studyApi.dto.StudyDTO;
import com.example.studyApi.dto.TagDTO;
import com.example.studyApi.dto.ZoneDTO;
import com.example.studyApi.dto.study.DescriptionDTO;
import com.example.studyApi.dto.study.MemberDTO;
import com.example.studyApi.repository.AccountRepository;
import com.example.studyApi.repository.StudyRepository;
import com.example.studyApi.repository.TagRepository;
import com.example.studyApi.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService{

    private final StudyRepository studyRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;
    private final ZoneRepository zoneRepository;


    @Override
    public String createStudy(StudyDTO studyDTO,String nickname) {
        Account account = accountRepository.findByNickname(nickname).get();
        Study study = modelMapper.map(studyDTO, Study.class);
        study.createStudy(account);
        studyRepository.save(study);

        return studyDTO.getTitle();
    }

    @Override
    public StudyDTO getStudy(String path) {
        Study study = studyRepository.findByPath(path).orElseThrow();
        StudyDTO dto = modelMapper.map(study, StudyDTO.class);
        return dto;
    }

    @Override
    public TagDTO addStudyTag(String path, TagDTO tagDTO) {
        Study study = studyRepository.findByPath(path).orElseThrow();
        Tag addTag = tagRepository.findByTitle(tagDTO.getTitle()).orElseGet(() -> {
            Tag tag = Tag.builder()
                    .title(tagDTO.getTitle())
                    .build();
            return tagRepository.save(tag);
        });
        study.getTags().add(addTag);
        return tagDTO;
    }
    @Override
    public List<Tag> getTags(String path) {
        List<Tag> tags = studyRepository.findOnlyTag(path);
        return tags;
    }

    @Override
    public List<Zone> getZones(String path) {
        List<Zone> tags = studyRepository.findOnlyZone(path);
        return tags;
    }

    @Override
    public void addZone(List<ZoneDTO> zoneData, String path) {
        Study study = studyRepository.findStudyWithZonesByPath(path).get();
        List<String> collect = zoneData.stream().map(ZoneDTO::getValue).collect(Collectors.toList());
        Set<Zone> zones = zoneRepository.findByLocalNameOfCityIn(collect);
        zones.forEach(zone -> study.getZones().add(zone));
    }

    @Override
    public boolean publishStudy(String path, String nickname) {
        Study study = studyRepository.findStudyWithManagerByPath(path).get();
        boolean result = false;
        if(study.getManager().getNickname().equals(nickname)){
            study.publishStudy();
            result = true;
        }
        return result;
    }

    @Override
    public boolean closeStudy(String path, String nickname) {
        Study study = studyRepository.findStudyWithManagerByPath(path).get();
        boolean result = false;
        if(study.getManager().getNickname().equals(nickname)){
            study.closeStudy();
            result = true;
        }
        return result;
    }

    @Override
    public DescriptionDTO studyListBoard(String path) {
        Study study = studyRepository.findStudyWithManagerByPath(path).get();
        DescriptionDTO descriptionDTO = DescriptionDTO.builder()
                .fullDescription(study.getFullDescription())
                .shortDescription(study.getShortDescription())
                .build();
        return descriptionDTO;
    }

    @Override
    public MemberDTO getStudyMembers(String path) {
        Study study = studyRepository.findStudyWithMemberByPath(path).get();
        MemberDTO map = modelMapper.map(study, MemberDTO.class);
        return map;
    }

    @Override
    public boolean joinStudy(String path, String nickname) {
        Study study = studyRepository.findStudyWithMemberByPath(path).get();
        Account account = accountRepository.findByNickname(nickname).get();
        if(studyRepository.existsByMembers(account)){
            return false;
        }
        study.getMembers().add(account);
        return true;
    }

    @Override
    public boolean leaveStudy(String path, String nickname) {
        Study study = studyRepository.findStudyWithMemberByPath(path).get();
        Account account = accountRepository.findByNickname(nickname).get();
        if(!studyRepository.existsByMembers(account)){
            return false;
        }
        study.getMembers().remove(account);
        return true;
    }
}
