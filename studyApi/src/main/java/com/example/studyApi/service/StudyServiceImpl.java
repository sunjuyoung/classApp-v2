package com.example.studyApi.service;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Study;
import com.example.studyApi.domain.Tag;
import com.example.studyApi.dto.StudyDTO;
import com.example.studyApi.dto.TagDTO;
import com.example.studyApi.repository.AccountRepository;
import com.example.studyApi.repository.StudyRepository;
import com.example.studyApi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService{

    private final StudyRepository studyRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;


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
        List<Tag> test = studyRepository.findTest(path);

        return test;
    }
}
