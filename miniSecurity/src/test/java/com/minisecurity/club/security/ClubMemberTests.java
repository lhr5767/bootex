package com.minisecurity.club.security;

import com.minisecurity.club.entity.ClubMember;
import com.minisecurity.club.entity.ClubMemberRole;
import com.minisecurity.club.repository.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {

    @Autowired
    private ClubMemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user"+i+"@mini.org")
                    .name("사용자"+i)
                    .fromSocial(false)
                    .password( passwordEncoder.encode("1111"))
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);

            //1-80 USER만 지정
            //81-90 USER,MANAGER
            //91-100 USER,MANAGER,ADMIN
            if(i>80) {
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }
            if(i>90) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            repository.save(clubMember);
        });
    }

    @Test
    public void testRead() {

        Optional<ClubMember> result = repository.findByEmail("user95@mini.org",false);

        ClubMember clubMember = result.get();

        System.out.println(clubMember);
    }
}
