package com.example.studyApi.repository;

import com.example.studyApi.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<Account> findByNickname(String nickname);

    @EntityGraph(attributePaths = {"roles"})
    Optional<Account> findByEmail(String email);

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"tags"})
    Account findAccountByNickname(String nickname);

    @EntityGraph(attributePaths = {"zones"})
    Account findZonesByNickname(String nickname);





}
