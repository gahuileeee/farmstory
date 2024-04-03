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

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {
    private final ArticleService articleService;

    // 공지사항 list
    @GetMapping("/community/notice_list")
    public String noticeList(Model model){

        List<ArticleDTO> articles = articleService.selectArticles();

        model.addAttribute("articles", articles);

        return "/community/notice_list";

    }

    @GetMapping("/community/notice_write")
    public String noticeWrite(){
        return "/community/notice_write";
    }

    // 공지사항 writer
    @PostMapping("/community/notice_write")
    public String noticeWrite(ArticleDTO articleDTO){
        log.info(articleDTO.toString());

        articleService.insertArticle(articleDTO);

        return "redirect:/community/notice_list";
    }



}