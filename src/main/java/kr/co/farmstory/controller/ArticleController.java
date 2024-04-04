package kr.co.farmstory.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.farmstory.config.AppInfo;
import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.dto.PageRequestDTO;
import kr.co.farmstory.dto.PageResponseDTO;
import kr.co.farmstory.entity.Config;
import kr.co.farmstory.repository.ConfigRepository;
import kr.co.farmstory.service.ArticleService;
import kr.co.farmstory.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ConfigRepository configRepository;
    /*
        @ModelAttribute("cate")
         - modelAttribute("cate", cate)와 동일
    */
    @GetMapping("/board/list")
    public String list(Model model){

        List<ArticleDTO> articles = articleService.selectArticles();
        log.info(articles.toString());

        model.addAttribute("articles", articles);

        return "/board/list";
    }

    @GetMapping("/board/write")
    public String write(){
        return "/board/write";
    }

    @PostMapping("/board/write")
    public String write(ArticleDTO articleDTO){
        log.info(articleDTO.toString());

        articleService.insertArticle(articleDTO);

        return "redirect:/board/write";
    }

}
