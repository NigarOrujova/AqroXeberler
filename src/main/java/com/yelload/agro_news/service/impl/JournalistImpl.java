package com.yelload.agro_news.service.impl;

import com.yelload.agro_news.repository.ImageRepository;
import com.yelload.agro_news.repository.JournalistRepository;
import com.yelload.agro_news.repository.NewsRepository;
import com.yelload.agro_news.service.JournalistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;

public class JournalistImpl extends JournalistService {
    private final JpaRepository _journalistRepositry;
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.profiles.active}")
    private String env;

    public JournalistImpl(JournalistRepository journalistRepository) {
        _journalistRepositry = journalistRepository;
    }

}
