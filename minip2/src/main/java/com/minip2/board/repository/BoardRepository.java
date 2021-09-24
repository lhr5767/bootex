package com.minip2.board.repository;

import com.minip2.board.entity.Board;
import com.minip2.board.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, SearchBoardRepository {

    @Query("select b, w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);


    //Board입장에선 Reply객체를 참조하지 않음 이때는 직접조인
    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno =:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value="select b, w, count(r)" +
                 " from Board b" +
                 " left join b.writer w" +
                 " left join Reply r on r.board = b" +
                 " group by b",
                  countQuery = "select count(b) from Board b")
    Page<Object[]> getBoarWithReplyCount(Pageable pageable); //목록 화면에 필요한 데이터

    @Query(value = "select b, w, count(r)"+
                    " from Board b left join b.writer w "+
                    " left outer join Reply r on r.board = b" +
                    " where b.bno =:bno")
    Object getBoardByBno(@Param("bno") Long bno); //조회 화면에 필요한 데이터
}
