package com.example.studyApi.service;

import com.example.studyApi.dto.StudyDTO;

public interface StudyService {

    String createStudy(StudyDTO studyDTO,String nickname);
}
