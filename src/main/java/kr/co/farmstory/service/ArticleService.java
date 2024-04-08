package kr.co.farmstory.service;

import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.dto.ArticlePageRequestDTO;
import kr.co.farmstory.dto.ArticlePageResponseDTO;
import kr.co.farmstory.dto.ConfigDTO;
import kr.co.farmstory.entity.Article;
import kr.co.farmstory.entity.Config;
import kr.co.farmstory.repository.ArticleRepository;
import kr.co.farmstory.repository.ConfigRepository;
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
    private final ConfigRepository configRepository;
    private final ModelMapper modelMapper;

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

    // ArticleService 클래스 내부에 구현합니다.
    public void insertArticle(ArticleDTO articleDTO) {
        Article article = articleDTO.toEntity();
        articleRepository.save(article);
    }

    public ArticleDTO findById(int no){

        Optional<Article> optArticle = articleRepository.findById(no);

        ArticleDTO articleDTO = null;

        if(optArticle.isPresent()){

            Article article = optArticle.get();
            articleDTO = modelMapper.map(article, ArticleDTO.class);
        }

        return articleDTO;
    }

    public ArticlePageResponseDTO getArticles(ArticlePageRequestDTO requestDTO) {
        return new ArticlePageResponseDTO();
    }


    public ArticlePageResponseDTO selectArticles(ArticlePageRequestDTO pageRequestDTO){

        log.info("selectArticles...1");
        Pageable pageable = pageRequestDTO.getPageable("no");

        log.info("selectArticles...2");
        Page<Tuple> pageArticle = articleRepository.selectArticles(pageRequestDTO, pageable);

        log.info("selectArticles...3 : " + pageArticle);
        List<ArticleDTO> dtoList = pageArticle.getContent().stream()
                .map(tuple ->
                        {
                            log.info("tuple : " + tuple);
                            Article article = tuple.get(0, Article.class);
                            String nick = tuple.get(1, String.class);
                            article.setNick(nick);

                            log.info("article : " + article);

                            return modelMapper.map(article, ArticleDTO.class);
                        }
                )
                .toList();

        log.info("selectArticles...4 : " + dtoList);

        int total = (int) pageArticle.getTotalElements();

        return ArticlePageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    public ArticlePageResponseDTO searchArticles(ArticlePageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");
        Page<Tuple> pageArticle = articleRepository.searchArticles(pageRequestDTO, pageable);

        List<ArticleDTO> dtoList = pageArticle.getContent().stream()
                .map(tuple ->
                        {
                            log.info("tuple : " + tuple);
                            Article article = tuple.get(0, Article.class);
                            String nick = tuple.get(1, String.class);
                            article.setNick(nick);

                            log.info("article : " + article);

                            return modelMapper.map(article, ArticleDTO.class);
                        }
                )
                .toList();

        int total = (int) pageArticle.getTotalElements();

        return ArticlePageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
    public ArticleDTO selectArticle(int no){
        return modelMapper.map(articleRepository.findById(no), ArticleDTO.class);
    }

    public void modifyArticle(ArticleDTO articleDTO){
        Article oArticle = articleRepository.findById(articleDTO.getNo()).get();
        ArticleDTO oArticleDTO = modelMapper.map(oArticle, ArticleDTO.class);


        oArticleDTO.setContent(articleDTO.getContent());
        oArticleDTO.setTitle(articleDTO.getTitle());


        Article article = modelMapper.map(oArticleDTO, Article.class);
        articleRepository.save(article);
    }




    @Transactional
    public void deleteArticle (int no){
        articleRepository.deleteById(no);
        articleRepository.deleteArticlesByParent(no);
    }







}
