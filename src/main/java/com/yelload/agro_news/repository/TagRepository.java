package com.yelload.agro_news.repository;

import com.yelload.agro_news.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag, Long> {
}
