package kr.co.farmstory.controller;

import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.service.ArticleService;
import kr.co.farmstory.dto.PageRequestDTO;
import kr.co.farmstory.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;
    
    // 공지사항 list
    @GetMapping("/community/notice_list")
    public String notice(Model model, PageRequestDTO pageRequestDTO){
        PageResponseDTO pageResponseDTO = articleService.findByParentAndCate(pageRequestDTO);
        log.info("pageResponseDTO : " + pageResponseDTO);
        model.addAttribute(pageResponseDTO);
        return "/community/notice_list";
    }

    @GetMapping("/community/notice_write")
    public String write(@ModelAttribute("cate") String cate){
        return "community/notice_write";
    }



}