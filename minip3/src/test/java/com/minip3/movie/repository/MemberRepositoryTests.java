package com.minip3.movie.repository;

import com.minip3.movie.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1,100).forEach(i-> {
            Member member = Member.builder()
                    .email("r" + i + "@zero.org")
                    .pw("1111")
                    .nickname("reviewer"+i).build();
            memberRepository.save(member);
        });
    }

    //특정회원 삭제시 review를 먼저 삭제하고 member를 삭제 해야함 이것을 하나의 트랜잭션으로 처리
    @Commit
    @Transactional
    @Test
    public void testDeleteMember() {

        Long mid = 3L;

        Member member = Member.builder().mid(mid).build();

        //memberRepository.deleteById(mid); 멤버를 먼저 삭제하면 오류(PK쪽을 먼저 삭제 할수 없음)
        //reviewRepository.deleteByMember(member);

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);

        //테스트시 review 데이터가 5개라면 5번의 쿼리가 발생되어 비효율 -> @Query이용해 where절 지정하면 한번에 처리됨
    }
}
