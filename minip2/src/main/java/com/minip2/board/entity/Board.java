package com.minip2.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //writer 출력하려면 member테이블 연결이 필요하기때문에 제외함
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    private Member writer;


    public void changeTitle(String title) {
        this.title=title;
    }

    public void changeContent(String content) {
        this.content=content;
    }
}
