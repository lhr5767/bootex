package com.minp.guestbook.service;

import com.minp.guestbook.dto.GuestBookDTO;
import com.minp.guestbook.dto.PageRequestDTO;
import com.minp.guestbook.dto.PageResultDTO;
import com.minp.guestbook.entity.GuestBook;

public interface GuestbookService {

    //dto 전달받아 새 방명록 등록
    Long register(GuestBookDTO dto);

    PageResultDTO<GuestBookDTO,GuestBook> getList(PageRequestDTO requestDTO);

    //jpa로 처리하려면 dto를 엔티티 객체로 변환하는 작업이 필요함
    default GuestBook dtoToEntity(GuestBookDTO dto){
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
    //엔티티 객체를 DTO객체로 변환
    default GuestBookDTO entityToDto(GuestBook entity) {
        GuestBookDTO dto = GuestBookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .build();
        return dto;
    }

    GuestBookDTO read(Long gno);

    void remove(Long gno);

    void modify(GuestBookDTO dto);
}
