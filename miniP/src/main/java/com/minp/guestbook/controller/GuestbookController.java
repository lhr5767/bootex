package com.minp.guestbook.controller;

import com.minp.guestbook.dto.GuestBookDTO;
import com.minp.guestbook.dto.PageRequestDTO;
import com.minp.guestbook.service.GuestbookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
public class GuestbookController {

    @Autowired
    private GuestbookService service;

    @GetMapping("/")
    public String index() {

        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result",service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get");
    }

    @PostMapping("/register")
    public String registerPost(GuestBookDTO dto, RedirectAttributes redirectAttributes) {

        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg",gno); //한번만 데이터를 전달 하는 용도 msg를 이용해 모달 창 보여줌

        return "redirect:/guestbook/list"; //등록후 목록 페이지로 이동
    }

    @GetMapping({"/read","/modify"}) //조회
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO , Model model) {

        GuestBookDTO dto = service.read(gno);

        model.addAttribute("dto",dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestBookDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        service.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno",dto.getGno());

        return "redirect:/guestbook/read";
    }
}
