package kr.co.farmstory.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.entity.Config;
import kr.co.farmstory.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/introduction/hello")
    public String introductionHello(@RequestParam("cate") String cate, Model model) {
        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);
        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName" , cateName);
        return "/introduction/hello";
    }

    @GetMapping("/introduction/direction")
    public String introductionDirection(@RequestParam("cate") String cate, Model model) {
        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);
        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName" , cateName);
        return "/introduction/direction";
    }

    @GetMapping("/croptalk/list")
    public String croptalkList(@RequestParam("cate") String cate, Model model) {
        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);
        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName" , cateName);
        return "/croptalk/list";
    }

    @GetMapping("/event/list")
    public String eventList(@RequestParam("cate") String cate, Model model) {
        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);
        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName" , cateName);
        return "/event/list";
    }

    @GetMapping("/community/list")
    public String communityList(@RequestParam("cate") String cate, Model model) {
        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);
        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName" , cateName);
        return "/community/list";
    }


    @GetMapping("/croptalk/write/{cate}")
    public String writeForm(@PathVariable("cate") String cate, Model model, HttpSession session) {
        // 세션에서 사용자 정보를 가져와서 모델에 추가
        String nick = (String) session.getAttribute("nick");
        model.addAttribute("nick", nick);

        // cate 속성을 직접 모델에 추가
        model.addAttribute("cate", cate);

        // 글쓰기 폼을 반환
        return "/croptalk/write";
    }

    /*
    @PostMapping("/croptalk/write/{cate}")
    public String write(@PathVariable("cate") String cate, ArticleDTO articleDTO, HttpSession session) {
        // 글쓰기 작업을 수행하고 해당 카테고리의 목록 페이지로 리다이렉트
        articleDTO.setWriter((String) session.getAttribute("userId")); // 세션에서 사용자 정보를 가져와서 작성자로 설정
        articleService.insertArticle(articleDTO, cate);
        return "redirect:/croptalk/list?cate=" + cate;
    }
     */
}
