package com.minisecurity.club.controller;


import com.minisecurity.club.security.dto.ClubAuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @PreAuthorize("permitAll()")
    @GetMapping("/all") //로그인 하지 않은 사용자도 접근가능
    public void exAll() {
        log.info("exAll --------");
    }

    @GetMapping("/member") //로그인한 사용자만
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info("exMember ---------");

        log.info("===============================");
        log.info(clubAuthMemberDTO); //컨트롤러에서 로그인된 사용자 정보 확인
    }

    @PreAuthorize("hasRole('ADMIN')") //어노테이션 기반으로 url에 접근제한 설정
    @GetMapping("/admin") // 관리자만
    public void exAdmin() {
        log.info("exAdmin --------------");
    }


    //로그인한 사용자중 user95@mini.org만 접근 가능능
   @PreAuthorize("#clubAuthMemberDTO !=null && #clubAuthMemberDTO.username eq \"user95@mini.org\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {

        log.info("exMemberonly ---------");
        log.info(clubAuthMemberDTO);

        return "/sample/admin";
    }
}
