package com.minp.guestbook.repository;

import com.minp.guestbook.entity.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

//querydsl 사용시에 추가로 상속이 필요
public interface GuestBookRepository extends JpaRepository<GuestBook,Long> , QuerydslPredicateExecutor<GuestBook> {
}
