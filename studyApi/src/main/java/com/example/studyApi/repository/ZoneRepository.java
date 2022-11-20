package com.example.studyApi.repository;

import com.example.studyApi.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ZoneRepository extends JpaRepository<Zone,Long> {

    Set<Zone> findByLocalNameOfCityIn(List<String> localNameOfCity);
}
