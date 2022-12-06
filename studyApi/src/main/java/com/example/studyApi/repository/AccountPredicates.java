package com.example.studyApi.repository;

import com.example.studyApi.domain.QAccount;
import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import com.querydsl.core.types.Predicate;

import java.util.Set;

public class AccountPredicates {

    public static Predicate findByTagsAndZones(Set<Tag> tags, Set<Zone> zones){
        QAccount account = QAccount.account;
        return account.zones.any().in(zones).and(account.tags.any().in(tags));
    }
}
