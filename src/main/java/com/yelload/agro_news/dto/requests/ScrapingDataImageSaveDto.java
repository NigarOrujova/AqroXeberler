package com.yelload.agro_news.dto.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yelload.agro_news.dto.NewsDto;

public class ScrapingDataImageSaveDto {
    private String name;
    private String link;
    private String fileType;
    private Boolean isMainImage;
    private NewsScrapingDataSaveDto newsDto;

    public ScrapingDataImageSaveDto(String name, String link, String fileType, Boolean isMainImage, NewsScrapingDataSaveDto newsDto) {
        this.name = name;
        this.link = link;
        this.fileType = fileType;
        this.isMainImage = isMainImage;
        this.newsDto = newsDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Boolean getMainImage() {
        return isMainImage;
    }

    public void setMainImage(Boolean mainImage) {
        isMainImage = mainImage;
    }

    public NewsScrapingDataSaveDto getNewsDto() {
        return newsDto;
    }

    public void setNewsDto(NewsScrapingDataSaveDto newsDto) {
        this.newsDto = newsDto;
    }
}
