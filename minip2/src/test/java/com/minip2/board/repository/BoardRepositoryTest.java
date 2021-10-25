package com.minip2.board.repository;

import com.minip2.board.entity.Board;
import com.minip2.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {

        IntStream.rangeClosed(1,100).forEach(i-> {

            //member 테스트 에서 만든 객체 이용
            Member member = Member.builder().email("usr"+i+"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title--"+i)
                    .content("Content--"+i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Transactional
    @Test
    public void testRead() {

        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();

        //지연로딩으로 board만 가져오는건 문제없음
        System.out.println(board);

        //member 테이블 가져와야 하는데 지연로딩이라 에러발생->트랜잭션 처리해줌
        System.out.println(board.getWriter());

    }

    @Test
    public void testReadWithWriter() {

        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[]) result ;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply() {

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoarWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;

            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead2() {

        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1() {

        boardRepository.search1();
    }

    @Test
    public void testSearchPage() {

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending().and(Sort.by("title").ascending()));

        //제목(t)에 1이 포함된 데이터 검색
        Page<Object[]> result = boardRepository.searchPage("t","1",pageable);
    }


}
