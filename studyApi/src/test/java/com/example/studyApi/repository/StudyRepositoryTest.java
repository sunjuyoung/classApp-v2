package com.example.studyApi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudyRepositoryTest {

    @Autowired
    private StudyRepository studyRepository;

    @Test
    public void test(){
        studyRepository.findOnlyTagByPath("test");
    }

}