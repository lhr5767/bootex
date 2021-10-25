package com.minip2.board.repository;

import com.minip2.board.entity.Board;
import com.minip2.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {

        IntStream.rangeClosed(1,300).forEach(i-> {

            //1부터 100까지 임의의 번호 (현재 db에 bno가 1부터 100까지 생성되어있음)
            long bno = (long)(Math.random()*100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("reply--"+i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }

    //연관관계 형성 되어있으면 내부적으로 조인이 일어남 이경우는 board,member 까지 다 가져옴
    @Test
    public void readReply() {
        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());
    }

    @Test
    public void testListByBoard() {

        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(97L).build());

        replyList.forEach(reply -> System.out.println(reply));
    }
}
