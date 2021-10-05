package com.minp.guestbook.service;

import com.minp.guestbook.dto.GuestBookDTO;
import com.minp.guestbook.dto.PageRequestDTO;
import com.minp.guestbook.dto.PageResultDTO;
import com.minp.guestbook.entity.GuestBook;
import com.minp.guestbook.entity.QGuestBook;
import com.minp.guestbook.repository.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
public class GuestbookServiceImpl implements  GuestbookService{

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Override
    public Long register(GuestBookDTO dto) {
        log.info("DTO--------------------------");
        log.info(dto);

        GuestBook entity = dtoToEntity(dto);

        log.info(entity);

        guestBookRepository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색 조건 처리

        Page<GuestBook> result = guestBookRepository.findAll(booleanBuilder,pageable); //querydsl 사용

        Function<GuestBook,GuestBookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {
        Optional<GuestBook> result = guestBookRepository.findById(gno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        guestBookRepository.deleteById(gno);
    }

    @Override
    public void modify(GuestBookDTO dto) {

        Optional<GuestBook> result = guestBookRepository.findById(dto.getGno());

        if(result.isPresent()) {
            GuestBook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            guestBookRepository.save(entity);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestBook qGuestBook = QGuestBook.guestBook;

        BooleanExpression expression = qGuestBook.gno.gt(0L); // gno > 0 조건만

        booleanBuilder.and(expression);

        //검색 조건이 없는 경우
        if(type==null || type.trim().length()==0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        //검색 조건
        if(type.contains("t")) { //t - 제목
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if(type.contains("c")) { // c - 내용
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if(type.contains("w")) { // w - 작성자
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }
        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
