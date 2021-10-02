package com.minip2.board.repository;

import com.minip2.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    //연관관계 있을시 pk부터(member부터 테스트)
    @Test
    public void insertMemberTest() {

        IntStream.rangeClosed(1,100).forEach(i-> {

            Member member = Member.builder()
                    .email("usr"+i+"@aaa.com")
                    .password("1111")
                    .name("USER"+i)
                    .build();

            memberRepository.save(member);

        });
    }
}
