package com.example.apilec.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2,message = "두글자 이상만 가능합니다")
    private String name;
    @Past
    private Date joinDate;

    @JsonIgnore // 외부 노출 x
    private String password;

    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

<<<<<<< Updated upstream
    public User(int id, String name, Date joinDate, String password, String ssn) {
=======
    public User(Integer id,  String name, Date joinDate, String password, String ssn) {
>>>>>>> Stashed changes
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
