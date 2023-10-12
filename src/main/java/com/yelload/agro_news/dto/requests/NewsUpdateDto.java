package com.yelload.agro_news.dto.requests;

import com.yelload.agro_news.dto.ImageDto;
import com.yelload.agro_news.dto.TagDto;

import java.util.List;
import java.util.Set;

public class NewsUpdateDto {
    private String title;
    private String author;
    private String content;
    private String date;
    private String newsSource;
    private Boolean isPublic;
    private Boolean isMainNews;
    private Boolean isTrendingNews;
    private List<ImageDto> imageDtos;
    private Set<TagDto> tagDtoSet;
    public NewsUpdateDto() {
    }

    public NewsUpdateDto(String title, String author, String content, String date, String newsSource, Boolean isPublic, Boolean isMainNews, Boolean isTrendingNews, List<ImageDto> imageDtos, Set<TagDto> tagDtoSet) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
        this.isPublic = isPublic;
        this.isMainNews = isMainNews;
        this.isTrendingNews = isTrendingNews;
        this.imageDtos = imageDtos;
        this.tagDtoSet = tagDtoSet;
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

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getMainNews() {
        return isMainNews;
    }

    public void setMainNews(Boolean mainNews) {
        isMainNews = mainNews;
    }

    public Boolean getTrendingNews() {
        return isTrendingNews;
    }

    public void setTrendingNews(Boolean trendingNews) {
        isTrendingNews = trendingNews;
    }

    public List<ImageDto> getImageDtos() {
        return imageDtos;
    }

    public void setImageDtos(List<ImageDto> imageDtos) {
        this.imageDtos = imageDtos;
    }

    public Set<TagDto> getTagDtoSet() {
        return tagDtoSet;
    }

    public void setTagDtoSet(Set<TagDto> tagDtoSet) {
        this.tagDtoSet = tagDtoSet;
    }
}
