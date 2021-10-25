package com.minip2.board.service;

import com.minip2.board.dto.ReplyDTO;
import com.minip2.board.entity.Board;
import com.minip2.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO); //댓글 등록

    List<ReplyDTO> getList(Long bno); //특정 게시글의 댓글 목록

    void modify(ReplyDTO replyDTO); //댓글 수정

    void remove(Long rno); //댓글 삭제

    //ReplyDTO -> Reply (Board객체 처리 필요)
    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();

        return reply;
    }

    //Reply -> ReplyDTO (Board객체 필요없음)
    default ReplyDTO entityToDTO(Reply reply) {

        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .createdDate(reply.getCreatedDate())
                .updatedDate(reply.getUpdatedDate())
                .build();
        return dto;
    }
}
