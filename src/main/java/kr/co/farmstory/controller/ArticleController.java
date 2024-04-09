package kr.co.farmstory.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.dto.ArticlePageRequestDTO;
import kr.co.farmstory.dto.ArticlePageResponseDTO;
import kr.co.farmstory.entity.Config;
import kr.co.farmstory.service.ArticleService;
import kr.co.farmstory.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final FileService fileService;

    // 팜스토리 소개 -----------------------------------------------------------------------------------------------------
    // 인사말
    @GetMapping("/introduction/hello")
    public String introductionHello(@RequestParam("cate") String cate, Model model) {

        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);

        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName", cateName);

        return "/introduction/hello";
    }

    // 찾아오시는길
    @GetMapping("/introduction/direction")
    public String introductionDirection(@RequestParam("cate") String cate, Model model) {
        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);

        model.addAttribute("articles", articles);
        Config cateName = articleService.findCate(cate);

        model.addAttribute("cateName", cateName);

        return "/introduction/direction";
    }

    // -----------------------------------------------------------------------------------------------------------


    @GetMapping("/event/list")
    public String eventList(@RequestParam("cate") String cate, Model model) {

        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);

        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName", cateName);

        return "/event/list";
    }

    @GetMapping("/community/list")
    public String communityList(@RequestParam("cate") String cate, Model model) {

        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);

        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName", cateName);

        return "/community/list";
    }

    @GetMapping("/event/write")
    public String eventWriteForm(@RequestParam("cate") String cate, Model model, HttpSession session) {

        String nick = (String) session.getAttribute("nick");
        model.addAttribute("nick", nick);

        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName", cateName);

        return "/event/write";
    }

    @GetMapping("/community/write")
    public String communityWriteForm(@RequestParam("cate") String cate, Model model, HttpSession session) {

        String nick = (String) session.getAttribute("nick");
        model.addAttribute("nick", nick);

        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName", cateName);

        return "/community/write";
    }

    @PostMapping("/event/write")
    public String eventWrite(@ModelAttribute ArticleDTO articleDTO, HttpServletRequest request) {

        articleDTO.setRegip(request.getRemoteAddr());
        articleService.insertArticle(articleDTO);

        return "redirect:/event/list?cate=" + articleDTO.getCate();
    }

    @PostMapping("/community/write")
    public String communityWrite(@ModelAttribute ArticleDTO articleDTO, HttpServletRequest request) {

        articleDTO.setRegip(request.getRemoteAddr());
        articleService.insertArticle(articleDTO);

        return "redirect:/community/list?cate=" + articleDTO.getCate();
    }

    // ------------------------------------------------------------------------------------------------------
    // 농작물이야기
    @GetMapping("/croptalk/list")
    public String croptalkList(@RequestParam("cate") String cate, Model model,
                               ArticlePageRequestDTO pageRequestDTO, @RequestParam(value = "pg", required = false) Integer pg) {

        ArticlePageResponseDTO pageResponseDTO;

        if(pageRequestDTO.getType() == null){
            if(pg != null){
                pageRequestDTO.setPg(pg);
            }
            // 일반 글 목록 조회
            pageResponseDTO = articleService.selectArticles(pageRequestDTO);
        }else{
            if(pg != null){
                pageRequestDTO.setPg(pg);
            }
            // 검색 글 목록 조회
            pageResponseDTO = articleService.searchArticles(pageRequestDTO);
            model.addAttribute("keyword", pageRequestDTO.getKeyword());
            model.addAttribute("type", pageRequestDTO.getType());
        }
        model.addAttribute(pageResponseDTO);

        List<ArticleDTO> articles = articleService.getArticlesByCategory(cate);
        model.addAttribute("articles", articles);

        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName", cateName);

        return "/croptalk/list";
    }

    @GetMapping("/croptalk/write")
    public String croptalkWriteForm(@RequestParam("cate") String cate, Model model, HttpSession session) {

        String nick = (String) session.getAttribute("nick");
        model.addAttribute("nick", nick);

        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName", cateName);

        return "/croptalk/write";
    }

    @PostMapping("/croptalk/write")
    public String croptalkWrite(@ModelAttribute ArticleDTO articleDTO, HttpServletRequest request) {

        articleDTO.setRegip(request.getRemoteAddr());
        articleService.insertArticle(articleDTO);

        log.info(articleDTO.toString());

        return "redirect:/croptalk/list?cate=" + articleDTO.getCate();
    }

    @GetMapping("/croptalk/view")
    public String view(@RequestParam("cate") String cate, Model model, @RequestParam("no") int no) {

        ArticleDTO articleDTO = articleService.findById(no);
        Config cateName = articleService.findCate(cate);

        if (articleDTO != null) {
            model.addAttribute("articleDTO", articleDTO);
        }

        model.addAttribute("cateName", cateName);

        List<ArticleDTO> comments = articleService.selectComment(no);
        log.info("comments "+comments);
        model.addAttribute("cate", articleDTO.getCate());
        model.addAttribute("comments",comments);

        return "/croptalk/view";
    }

    @GetMapping("/croptalk/modify")
    public String modifyFrom(int no, @RequestParam("cate") String cate,  Model model){

        ArticleDTO articleDTO = articleService.selectArticle(no);
        model.addAttribute("article", articleDTO);

        Config cateName = articleService.findCate(cate);
        model.addAttribute("cateName", cateName);

        model.addAttribute("cate", articleDTO.getCate());

        return "/croptalk/modify";
    }

    @PostMapping("/croptalk/modify")
    public String modify(ArticleDTO articleDTO) {

        articleService.modifyArticle(articleDTO);

        return "redirect:/croptalk/view?no=" + articleDTO.getNo()+"&cate="+articleDTO.getCate();
    }

    @GetMapping("/croptalk/delete")
    public String delete(int no, String cate) {
        fileService.deleteFiles(no);
        articleService.deleteArticle(no);

        return "redirect:/croptalk/list?cate=" + cate;
    }

    //comment
    @PostMapping("/croptalk/insertComment" )
    public ResponseEntity insertComment(@RequestBody ArticleDTO commentDTO, HttpServletRequest request){
        commentDTO.setRegip(request.getRemoteAddr());
        log.info("info.. "+commentDTO);
        return articleService.insertComment(commentDTO);
    }

    @ResponseBody
    @DeleteMapping("/croptalk/deleteComment/{no}")
    public ResponseEntity deleteComment(@PathVariable("no") int no){
        return   articleService.deleteComment(no);
    }

    @ResponseBody
    @PutMapping("/croptalk/modifyComment")
    public ResponseEntity  modifyComment(@RequestBody ArticleDTO commentDTO){
        log.info("modify! "+commentDTO);
        ArticleDTO oldComment = articleService.selectCommentByNo(commentDTO.getNo());
        oldComment.setContent(commentDTO.getContent());

        return articleService.updateComment(oldComment);
    }

    @ResponseBody
    @GetMapping("/croptalk/selectComment/{no}")
    public ResponseEntity  selectComment(@PathVariable("no") int no){
        ArticleDTO articleDTO =articleService.selectCommentByNo(no);
        Map<String , Object> map = new HashMap<>();
        map.put("article", articleDTO);
        return ResponseEntity.ok().body(map);
    }
}