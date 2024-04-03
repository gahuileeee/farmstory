package kr.co.farmstory.service;
import jakarta.transaction.Transactional;
import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.dto.PageRequestDTO;
import kr.co.farmstory.dto.PageResponseDTO;
import kr.co.farmstory.entity.Article;
import kr.co.farmstory.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<ArticleDTO> selectArticles(){

        List<Article> articles = articleRepository.findAll();

        List<ArticleDTO> articleDTOs = articles.stream()
                .map(entity -> ArticleDTO.builder()
                        .no(entity.getNo())
                        .title(entity.getTitle())
                        .writer(entity.getWriter())
                        .rdate(entity.getRdate())
                        .hit(entity.getHit())
                        .build())
                .collect(Collectors.toList());

        return articleDTOs;
    }
}