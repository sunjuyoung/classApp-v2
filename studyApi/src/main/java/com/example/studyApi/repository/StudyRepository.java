package com.example.studyApi.repository;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Study;
import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface StudyRepository extends JpaRepository<Study,Long> {

    boolean existsByPath(String path);

    @EntityGraph(value = "Study.withAll", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Study> findByPath(String path);

    @EntityGraph(attributePaths = {"tags"})
    Optional<Study> findStudyWithTagsByPath(String path);

    @EntityGraph(attributePaths = {"zones"})
    Optional<Study> findStudyWithZonesByPath(String path);

    @EntityGraph(attributePaths = {"members", "manager"})
    Optional<Study> findStudyWithMemberByPath(String path);

    @EntityGraph(attributePaths = {"manager"})
    Optional<Study> findStudyWithManagerByPath(String path);

    @Query(value = "SELECT t.tag_id,t.title " +
            "FROM study s " +
            "INNER JOIN study_tag st ON s.study_id = st.study_id  " +
            "INNER JOIN tag t ON t.tag_id = st.tag_id " +
            "WHERE s.path = :path", nativeQuery = true)
    Optional<Study> findOnlyTagByPath(@Param("path") String path);

    @Query("select t from Study s inner join s.tags t  where s.path = :path")
    List<Tag> findOnlyTag(@Param("path") String path);

    @Query("select z from Study s inner join s.zones z  where s.path = :path")
    List<Zone> findOnlyZone(@Param("path") String path);

    @EntityGraph(attributePaths = {"zones", "tags"})
    List<Study> findAll();

    boolean existsByMembers(Account members);

    @EntityGraph(attributePaths = {"zones", "tags"})
    Study findStudyWithTagsAndZonesById(Long id);
}