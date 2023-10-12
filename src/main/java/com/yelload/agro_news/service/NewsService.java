package com.yelload.agro_news.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yelload.agro_news.dto.NewsDto;
import com.yelload.agro_news.dto.requests.NewsScrapingDataSaveDto;
import com.yelload.agro_news.dto.requests.NewsUpdateDto;
import com.yelload.agro_news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NewsService {
    NewsDto getNews(Long id);

    List<NewsDto> getAllNewsByTitle(String title);

    News saveNews(NewsDto newsDto);

    News saveNews(String newsString, MultipartFile file, MultipartFile[] files) throws JsonProcessingException;

    void saveNewsFromScrapingData(NewsScrapingDataSaveDto newsDto);

    void deleteNews(Long id);

    void deleteAllNews();

    List<NewsDto> getMainNews();

    List<NewsDto> getTrendNews();

    List<NewsDto> getAllNewsBySize(Pageable pageable);

    NewsDto addTagToNews(Long newsId, Long tagId);

    void addMainImageToNews(Long newsId, MultipartFile image);

    void addImagesToNews(Long id, MultipartFile[] images);

    NewsUpdateDto updateNews(Long id, NewsUpdateDto newsUpdateDto);

    void deleteTagFromNews(Long tagId, Long newsId);

    Page<NewsDto> searchNewsByTitle(String keyWord, Pageable pageable);
    void saveNewsFromExcel(MultipartFile file) throws IOException;
}
