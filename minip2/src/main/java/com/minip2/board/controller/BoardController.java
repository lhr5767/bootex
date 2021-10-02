package com.minip2.board.controller;

import com.minip2.board.dto.BoardDTO;
import com.minip2.board.dto.PageRequestDTO;
import com.minip2.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list --" +pageRequestDTO);
        model.addAttribute("result",boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get --");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {

        log.info("boardDTO ---" + boardDTO);

        Long bno = boardService.register(boardDTO);

        log.info("BNO:" + bno);

        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }
    @GetMapping({"/read","/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,Long bno,Model model) {
        log.info("bno - " + bno);
        BoardDTO boardDTO = boardService.get(bno);

        log.info(boardDTO);

        model.addAttribute("dto",boardDTO);
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes) {

        log.info("bno: " + bno);

        boardService.removeWithReplies(bno);

        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO , @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {

        log.info("post modify --------------------------------");
        log.info("boardDTO :" + boardDTO);

        boardService.modify(boardDTO);

        redirectAttributes.addFlashAttribute("page",requestDTO.getPage());
        redirectAttributes.addFlashAttribute("type",requestDTO.getType());
        redirectAttributes.addFlashAttribute("keyword",requestDTO.getKeyword());

        return "redirect/board/read";

    }
}
