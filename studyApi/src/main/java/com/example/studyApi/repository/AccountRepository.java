package com.example.studyApi.repository;

import com.example.studyApi.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<Account> findByNickname(String nickname);

}
