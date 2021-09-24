package com.minip2.board.repository;

import com.minip2.board.entity.Board;
import com.minip2.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    @Modifying //jpql 이용해 update,delete 실행하려면 필요함
    @Query("delete from Reply r where r.board.bno=:bno")
    void deleteByBno(Long bno);

    //댓글 가져오기 쿼리 메서드 사용
    List<Reply> getRepliesByBoardOrderByRno(Board board);

}
