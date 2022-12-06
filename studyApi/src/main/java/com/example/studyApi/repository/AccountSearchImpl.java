package com.example.studyApi.repository;

import com.example.studyApi.domain.Account;
import com.example.studyApi.domain.QAccount;
import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import com.example.studyApi.service.AccountServiceImpl;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Set;

public class AccountSearchImpl extends QuerydslRepositorySupport implements AccountSearch {

    public AccountSearchImpl() {
        super(Account.class);
    }

    @Override
    public Predicate findByTagsAndZones(Set<Tag> tags, Set<Zone> zones) {
        QAccount account = QAccount.account;
        return account.zones.any().in(zones).and(account.tags.any().in(tags));
    }
}
