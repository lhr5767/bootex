package com.example.apilec.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    private Integer id;

    @Size(min = 2,message = "두글자 이상만 가능합니다")
    private String name;
    @Past
    private Date joinDate;

    @JsonIgnore //데이터 제어
    private String password;
    @JsonIgnore
    private String ssn;
}
