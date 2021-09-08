package com.example.apilec.controller;

import com.example.apilec.domain.User;
import com.example.apilec.domain.UserV2;
import com.example.apilec.exception.UserNotFoundException;
import com.example.apilec.service.UserService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin") //uri 시작을 admin으로
public class AdminUserController {

    private UserService userService;

    //의존성 주입 방법중 하나
    public AdminUserController(UserService service) {
        this.userService=service;
    }

    @GetMapping("/users")
    public MappingJacksonValue findAllUsers() {

        List<User> users = userService.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filterProvider);

        return mapping;
    }


    //@GetMapping("/v1/users/{id}") //v1,v2 나눠서 uri로 api의 버전관리
    //@GetMapping(value = "/users/{id}/",params = "version=1") // request param 을 이용한 버전관리
    //@GetMapping(value = "/users/{id}",headers = "X-API-VERSION=1") //header 이용한 버전관리
    @GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue findUserV1(@PathVariable int id) {
        User user = userService.findOne(id);
        if(user==null) {
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filterProvider);
        return mapping;
    }

    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}/",params = "version=2")
    //@GetMapping(value = "/users/{id}",headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue findUserV2(@PathVariable int id) {
        User user = userService.findOne(id);
        if(user==null) {
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        //User -> User2 로 변환
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);
        userV2.setGrade("VIP");


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filterProvider);
        return mapping;
    }






}
