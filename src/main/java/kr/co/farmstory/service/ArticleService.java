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

    //카테고리 검색(카테고리에 해당하는 Config 엔티티를 찾아 반환)
    public Config findCate(String cate){
        return  configRepository.findById(cate).get();
    }

    // 해당 카테고리의 게시글 목록을 가져옴
    public List<ArticleDTO> getArticlesByCategory(String cate) {
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

}
