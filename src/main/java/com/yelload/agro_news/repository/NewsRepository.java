package com.yelload.agro_news.repository;

import com.yelload.agro_news.entity.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findNewsByTitle(String title);
    List<News> findNewsByIsMainNewsTrue();
    List<News> findNewsByIsTrendingNewsTrue();
    List<News> findFirst5ByOrderByCreatedAtDesc();
    List<News> findAllByOrderByCreatedAtDesc();
    List<News> findAllByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
