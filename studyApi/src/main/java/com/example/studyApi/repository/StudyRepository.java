package com.example.studyApi.repository;

import com.example.studyApi.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study,Long> {
    boolean existsByPath(String path);
}
