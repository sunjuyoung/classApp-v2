package com.example.studyApi.repository;

import com.example.studyApi.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {

//    List<Tag> findByAccount(String nickname);

    Optional<Tag> findByTitle(String title);
}
