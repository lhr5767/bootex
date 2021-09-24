package com.minip2.board.service;


import com.minip2.board.dto.BoardDTO;
import com.minip2.board.dto.PageRequestDTO;
import com.minip2.board.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {

        BoardDTO dto = BoardDTO.builder()
                .title("Test")
                .content("testCon")
                .writerEmail("usr1@aaa.com") //DB에 저장된 이메일
                .build();

        Long bno = boardService.register(dto);

    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO,Object[]> result = boardService.getList(pageRequestDTO);

        for(BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet() {
        Long bno = 100L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }

    @Test
    public void deleteTest() {
        Long bno = 90L;

        boardService.removeWithReplies(bno);
    }


    @Test
    public void modifyTest() {
        BoardDTO boardDTO = BoardDTO.builder()
                    .bno(4L)
                    .title("제목 변경")
                    .content("내용 수정")
                    .build();

        boardService.modify(boardDTO);
    }
}
