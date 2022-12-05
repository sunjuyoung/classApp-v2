package com.example.studyApi.repository;

import com.example.studyApi.domain.Event;
import com.example.studyApi.domain.Study;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface EventRepository extends JpaRepository<Event,Long> {

    @EntityGraph(attributePaths = {"enrollments"})
    List<Event> findByStudy(Study study);

    @EntityGraph(attributePaths = {"enrollments","study","manager"})
    Optional<Event> findById(Long id);

}
