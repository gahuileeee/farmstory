package kr.co.farmstory.controller;

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

    @GetMapping("/board/list")
    public String listArticles(@RequestParam String cate, Model model) {
        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);
        return "list"; // list.html 템플릿을 사용하여 게시글 목록을 표시
    }

    @GetMapping("/board/write")
    public String showWriteForm(Model model) {
        // 게시글 작성을 위한 폼을 보여주는 페이지로 이동
        model.addAttribute("articleDTO", new ArticleDTO());
        return "/croptalk/write"; // write.html 템플릿을 사용하여 게시글 작성 폼을 표시
    }

    @PostMapping("/board/write")
    public String submitArticle(@ModelAttribute ArticleDTO articleDTO) {
        // 게시글 작성 처리
        articleService.createArticle(articleDTO);
        return "redirect:/farmstory/board/list?cate=" + articleDTO.getCate();
    }

    @GetMapping("/board/modify/{articleId}")
    public String showModifyForm(@PathVariable Long articleId, Model model) {
        // 게시글 수정을 위한 폼을 보여주는 페이지로 이동
        ArticleDTO articleDTO = articleService.findArticleById(articleId);
        model.addAttribute("articleDTO", articleDTO);
        return "modify"; // modify.html 템플릿을 사용하여 게시글 수정 폼을 표시
    }

    @PostMapping("/board/modify/{articleId}")
    public String modifyArticle(@PathVariable Long articleId, @ModelAttribute ArticleDTO modifiedArticleDTO) {
        // 게시글 수정 처리
        articleService.modifyArticle(articleId, modifiedArticleDTO);
        return "redirect:/farmstory/board/list?cate=" + modifiedArticleDTO.getCate();
    }

    @PostMapping("/board/delete/{articleId}")
    public String deleteArticle(@PathVariable Long articleId) {
        // 게시글 삭제 처리
        articleService.deleteArticle(articleId);
        // 여기서는 redirect가 아니라 그냥 리스트를 보여주는 페이지로 이동하는 것이 좋습니다.
        return "redirect:/farmstory/board/list";
    }

    @GetMapping("/croptalk/story")
    public String croptalkStory(@RequestParam("cate") String cate, Model model) {
        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);
        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName" , cateName);
        return "/croptalk/list";
    }

}
