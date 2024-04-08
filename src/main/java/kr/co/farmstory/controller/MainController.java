package kr.co.farmstory.controller;

import kr.co.farmstory.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping(value = {"/", "/index"})
    public String index(Authentication authentication, Model model){
        model.addAttribute("grows", articleRepository.findTop5ByCate("grow"));
        model.addAttribute("school", articleRepository.findTop5ByCate("school"));
        model.addAttribute("story", articleRepository.findTop5ByCate("story"));

        model.addAttribute("notice", articleRepository.findTop3ByCate("notice"));
        model.addAttribute("qna", articleRepository.findTop3ByCate("qna"));
        model.addAttribute("faq", articleRepository.findTop3ByCate("faq"));
        return "/index";

    }
}
