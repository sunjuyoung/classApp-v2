package com.example.studyApi.repository;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface AccountSearch {

    Predicate findByTagsAndZones(Set<Tag> tags, Set<Zone> zones);
}
