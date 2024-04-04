package kr.co.farmstory.repository;

import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.entity.Article;
import kr.co.farmstory.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

}