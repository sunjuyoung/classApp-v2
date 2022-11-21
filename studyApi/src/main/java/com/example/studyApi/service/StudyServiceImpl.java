package com.example.studyApi.service;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Study;
import com.example.studyApi.dto.StudyDTO;
import com.example.studyApi.repository.AccountRepository;
import com.example.studyApi.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService{

    private final StudyRepository studyRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;


    @Override
    public String createStudy(StudyDTO studyDTO,String nickname) {
        Account account = accountRepository.findByNickname(nickname).get();
        Study study = modelMapper.map(studyDTO, Study.class);
        study.createStudy(account);
        studyRepository.save(study);
        return studyDTO.getTitle();
    }
}
