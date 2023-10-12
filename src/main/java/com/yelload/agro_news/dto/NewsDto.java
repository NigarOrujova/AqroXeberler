package com.yelload.agro_news.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class NewsDto {
    private Long id;
    @NotNull(message = "Title may not be null")
    private String title;
    private String author;
    @NotNull(message = "Content may not be null")
    private String content;
    private String date;
    @NotNull(message = "News source may not be null")
    private String newsSource;
    private Integer view;
    private List<ImageDto> images;
    private Set<TagDto> tags;

    private Boolean isPublic;
    private Boolean isMainNews;
    private Boolean isTrendingNews;


    public NewsDto(Long id, String title, String author, String content, String date, String newsSource, Integer view,
                   List<ImageDto> images, Set<TagDto> tags) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
        this.view = view;
        this.images = images;
        this.tags = tags;
    }

    public NewsDto(Long id, String title, String author, String content, String date, String newsSource) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
    }

    public NewsDto(String title, String author, String content, String date, String newsSource) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
    }

    public NewsDto(String title, String author, String content, String date, String newsSource, List<ImageDto> images) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
        this.images = images;
    }

    public NewsDto(String title, String author, String content, String date, String newsSource,
                   List<ImageDto> images, Set<TagDto> tags) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
        this.images = images;
        this.tags = tags;
    }

    public NewsDto(Long id, String title, String author, String content, String date, String newsSource, Integer view, List<ImageDto> images, Set<TagDto> tags, Boolean isPublic, Boolean isMainNews, Boolean isTrendingNews) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
        this.view = view;
        this.images = images;
        this.tags = tags;
        this.isPublic = isPublic;
        this.isMainNews = isMainNews;
        this.isTrendingNews = isTrendingNews;
    }

    public NewsDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    public Set<TagDto> getTags() {
        return tags;
    }

    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
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
}
