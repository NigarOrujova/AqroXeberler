package com.yelload.agro_news.dto.requests;

import com.yelload.agro_news.dto.ImageDto;

import java.util.List;

public class NewsScrapingDataSaveDto {
    private String title;
    private String author;
    private String content;
    private String date;
    private String newsSource;
    private List<ScrapingDataImageSaveDto> imageDtoList;

    public NewsScrapingDataSaveDto(String title, String author, String content, String date, String newsSource, List<ScrapingDataImageSaveDto> imageDtoList) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
        this.imageDtoList = imageDtoList;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public List<ScrapingDataImageSaveDto> getImageDtoList() {
        return imageDtoList;
    }

    public void setImageDtoList(List<ScrapingDataImageSaveDto> imageDtoList) {
        this.imageDtoList = imageDtoList;
    }
}
