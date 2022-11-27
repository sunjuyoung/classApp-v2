package com.example.studyApi.repository;

import com.example.studyApi.domain.Study;
import com.example.studyApi.domain.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study,Long> {

    boolean existsByPath(String path);

    @EntityGraph(value = "Study.withAll",type = EntityGraph.EntityGraphType.LOAD)
    Optional<Study> findByPath(String path);

    @EntityGraph(attributePaths = {"tags"})
    Optional<Study> findStudyWithTagsByPath(String path);

    @EntityGraph(attributePaths = {"zones"})
    Optional<Study> findStudyWithZonesByPath(String path);

    @EntityGraph(attributePaths = {"members","manager"})
    Optional<Study> findStudyWithMemberByPath(String path);

    @Query(value = "SELECT t.tag_id,t.title " +
            "FROM study s " +
            "INNER JOIN study_tag st ON s.study_id = st.study_id  " +
            "INNER JOIN tag t ON t.tag_id = st.tag_id " +
            "WHERE s.path = :path", nativeQuery = true)
    Optional<Study> findOnlyTagByPath(@Param("path") String path);

    @Query("select t from Study s inner join s.tags t  where s.path = :path")
    List<Tag> findTest(@Param("path") String path);
}
