package kr.co.farmstory.repository;

import kr.co.farmstory.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByCate(String cate);

    List<Article> findTop5ByCate(String cate);

    List<Article> findTop3ByCate(String cate);
}
