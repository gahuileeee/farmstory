package kr.co.farmstory.repository.custum;

import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.ArticlePageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {

    public Page<Tuple> selectArticles(ArticlePageRequestDTO pageRequestDTO, Pageable pageable);

    public Page<Tuple> searchArticles(ArticlePageRequestDTO pageRequestDTO, Pageable pageable);
}
