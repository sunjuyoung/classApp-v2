package com.example.studyApi.valid;

import com.example.studyApi.dto.StudyDTO;
import com.example.studyApi.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class StudyValidation implements Validator {

    private final StudyRepository studyRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return StudyDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        StudyDTO studyDTO = (StudyDTO)object;
        if(studyRepository.existsByPath(studyDTO.getPath())){

            errors.rejectValue("path","invalid.path",new Object[]{studyDTO.getPath()},"이미 사용중인 url 입니다.");
        }

    }
}
