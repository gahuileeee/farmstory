package kr.co.farmstory.controller;

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
    
    // market 리스트 출력
    @GetMapping("/market/list")
    public String list(Model model, PageRequestDTO pageRequestDTO){
        PageResponseDTO pageResponseDTO = articleService.findByParentAndCate(pageRequestDTO);
        log.info("pageResponseDTO : " + pageResponseDTO);
        model.addAttribute(pageResponseDTO);
        return "/market/list";
    }
}