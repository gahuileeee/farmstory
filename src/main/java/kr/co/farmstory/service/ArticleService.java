package kr.co.farmstory.service;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    // RootConfig Bean 생성/등록
    private final ModelMapper modelMapper;

    public PageResponseDTO findByParentAndCate(PageRequestDTO pageRequestDTO) {          // ArticleController - list로 감

        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Article> pageArticle = articleRepository.findByParentAndCate(0, pageRequestDTO.getCate(), pageable);
        List<ArticleDTO> dtoList = pageArticle.getContent().stream()
                .map(entity -> modelMapper.map(entity, ArticleDTO.class))
                .toList();
        int total = (int) pageArticle.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }


    public void insertArticle(ArticleDTO articleDTO) {

        // articleDTO를 articleEntity로 변환
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info(article.toString());

        // 저장 후 저장한 엔티티 객체 반환(사실 JPA sava() 메서드는 default로 저장한 Entity를 반환)
        Article savedArticle = articleRepository.save(article);
        log.info("insertArticle : " + savedArticle.toString());

    }
}