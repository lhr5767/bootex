package com.minisecurity.club.repository;

import com.minisecurity.club.entity.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember,String> {

    //일반 로그인 사용자와 소셜 로그인 사용자 구분
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.fromSocial =:social and m.email=:email")
    Optional<ClubMember> findByEmail(String email, boolean social);
}
