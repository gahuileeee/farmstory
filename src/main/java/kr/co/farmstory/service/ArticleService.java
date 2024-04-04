package kr.co.farmstory.service;

import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.entity.Article;
import kr.co.farmstory.repository.ArticleRepository;
import kr.co.farmstory.repository.ConfigRepository;
import kr.co.farmstory.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void insertArticle(ArticleDTO articleDTO){
        // DTO를 Entity로 변환
        Article article = articleDTO.toEntity();

        // Entity 저장(데이터베이스 Insert)
        articleRepository.save(article);
    }

    public List<ArticleDTO> selectArticles() {
        // 전체 조회
        List<Article> articles = articleRepository.findAll();

        List<ArticleDTO> articleDTOs = articles.stream()
                .map(entity -> ArticleDTO.builder()
                        .no(entity.getNo())
                        .title(entity.getTitle())
                        .nick(entity.getNick())
                        .rdate(entity.getRdate())
                        .hit(entity.getHit())
                        .build())
                .collect(Collectors.toList());

        return articleDTOs;
    }
}