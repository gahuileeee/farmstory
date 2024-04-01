package kr.co.farmstory.repository;

import kr.co.farmstory.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    public Page<Article> findByParentAndCate(int parent, String cate, Pageable pageable);

}