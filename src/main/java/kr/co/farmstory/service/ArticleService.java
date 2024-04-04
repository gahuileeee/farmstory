package kr.co.farmstory.service;

import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.dto.ConfigDTO;
import kr.co.farmstory.entity.Article;
import kr.co.farmstory.entity.Config;
import kr.co.farmstory.repository.ArticleRepository;
import kr.co.farmstory.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ConfigRepository configRepository;

    public List<ArticleDTO> getArticlesByCategory(String cate) {
        // 해당 카테고리의 게시글 목록을 가져온다.
        List<Article> articles = articleRepository.findByCate(cate);

        // Entity를 DTO로 변환하여 반환한다.
        return articles.stream()
                .map(article -> {
                    ArticleDTO dto = new ArticleDTO();
                    dto.setNo(article.getNo());
                    dto.setParent(article.getParent());
                    dto.setComment(article.getComment());
                    dto.setCate(article.getCate());
                    dto.setTitle(article.getTitle());
                    dto.setContent(article.getContent());
                    dto.setFile(article.getFile());
                    dto.setHit(article.getHit());
                    dto.setWriter(article.getWriter());
                    dto.setRegip(article.getRegip());
                    dto.setRdate(article.getRdate());
                    dto.setNick(article.getNick());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void createArticle(ArticleDTO articleDTO) {
        // DTO를 Entity로 변환하여 저장한다.
        Article article = articleDTO.toEntity();
        articleRepository.save(article);
    }

    public void modifyArticle(Long articleId, ArticleDTO modifiedArticleDTO) {
        // 수정할 게시글을 찾는다.
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        Article article = articleOptional.orElseThrow(() -> new IllegalArgumentException("Invalid article"));

        // 변경된 내용을 적용한다.
        article.setTitle(modifiedArticleDTO.getTitle());
        article.setContent(modifiedArticleDTO.getContent());

        // 수정된 게시글을 저장한다.
        articleRepository.save(article);
    }

    public void deleteArticle(Long articleId) {
        // 삭제할 게시글을 찾는다.
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        Article article = articleOptional.orElseThrow(() -> new IllegalArgumentException("Invalid article"));

        // 게시글을 삭제한다.
        articleRepository.delete(article);
    }

    public ArticleDTO findArticleById(Long articleId) {
        // 게시글을 찾아서 DTO로 변환하여 반환한다.
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        Article article = articleOptional.orElseThrow(() -> new IllegalArgumentException("Invalid article"));

        ArticleDTO dto = new ArticleDTO();
        dto.setNo(article.getNo());
        dto.setParent(article.getParent());
        dto.setComment(article.getComment());
        dto.setCate(article.getCate());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setFile(article.getFile());
        dto.setHit(article.getHit());
        dto.setWriter(article.getWriter());
        dto.setRegip(article.getRegip());
        dto.setRdate(article.getRdate());
        dto.setNick(article.getNick());

        return dto;
    }

    //카테고리를 검색하는 메소드 입니다
    public Config findCate(String cate){
        return  configRepository.findById(cate).get();
    }
}
