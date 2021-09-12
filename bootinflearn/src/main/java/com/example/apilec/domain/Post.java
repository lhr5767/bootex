package com.example.apilec.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

<<<<<<< Updated upstream
    @ManyToOne(fetch = FetchType.LAZY) //many~ 항상 지연로딩 사용할것
    @JsonIgnore
=======
    @ManyToOne(fetch = FetchType.LAZY) //Many~ 항상 지연로딩 사용할것
    @JsonIgnore //외부로 공개x
>>>>>>> Stashed changes
    private User user;
}
