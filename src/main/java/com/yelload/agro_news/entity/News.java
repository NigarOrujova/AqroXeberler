package com.yelload.agro_news.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long news_id;

    @Column(name = "title", length = 500)
    private String title;

    @Column(name = "auther")
    private String author;

    @Column(name = "content", length = 6000)
    private String content;

    @Column(name = "date")
    private String date;

    @Column(name = "news_source")
    private String newsSource;

    @Column(name = "news_view")
    private Integer view = 0;

    // Relations
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "news", cascade = CascadeType.ALL)
    List<Image> images;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinTable(
            name = "news_tag",
            joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;


    // Moderators
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private Date updatedOn;

    @Column(name = "is_published")
    private Boolean isPublic = false;

    @Column(name = "main_news")
    private Boolean isMainNews = false;

    @Column(name = "trending_news")
    private Boolean isTrendingNews = false;


    public News(Long news_id, String title, String author, String content, String date, String newsSource,
                List<Image> images, Set<Tag> tags, Date createdAt, Date updatedOn, Boolean isPublic,
                Boolean isMainNews, Boolean isTrendingNews) {
        this.news_id = news_id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
        this.images = images;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedOn = updatedOn;
        this.isPublic = isPublic;
        this.isMainNews = isMainNews;
        this.isTrendingNews = isTrendingNews;
    }

    public News(Long news_id, String title, String author, String content, String date, String newsSource,
                Integer view, List<Image> images, Set<Tag> tags, Date createdAt, Date updatedOn, Boolean isPublic,
                Boolean isMainNews, Boolean isTrendingNews) {
        this.news_id = news_id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
        this.view = view;
        this.images = images;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedOn = updatedOn;
        this.isPublic = isPublic;
        this.isMainNews = isMainNews;
        this.isTrendingNews = isTrendingNews;
    }

    public News(Long news_id, String title, String author, String content, String date, String newsSource) {
        this.news_id = news_id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
    }

    public News(String title, String author, String content, String date, String newsSource) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
    }

    public News(String title, String author, String content, String date, String newsSource, Boolean isPublic, Boolean isMainNews, Boolean isTrendingNews) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
        this.isPublic = isPublic;
        this.isMainNews = isMainNews;
        this.isTrendingNews = isTrendingNews;
    }



    public News(String title, String author, String content, String date, String newsSource, List<Image> images, Set<Tag> tags, Boolean isPublic, Boolean isMainNews, Boolean isTrendingNews) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.newsSource = newsSource;
        this.images = images;
        this.tags = tags;
        this.isPublic = isPublic;
        this.isMainNews = isMainNews;
        this.isTrendingNews = isTrendingNews;
    }


    public News() {
    }

    public Long getNews_id() {
        return news_id;
    }

    public void setNews_id(Long news_id) {
        this.news_id = news_id;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }
}
