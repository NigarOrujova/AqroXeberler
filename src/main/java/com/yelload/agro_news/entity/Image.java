package com.yelload.agro_news.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "image_name")
    private String name;

    @Column(name = "image_link", length = 500)
    private String link;

    @Column(name = "file_type")
    private String fileType;

//    @Type(type = "org.hibernate.type.BinaryType")
    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "is_main_image")
    private Boolean isMainImage = false;

    // Relations
    @JsonIgnore
    @ManyToOne
    private News news;

    // Moderators
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private Date updatedOn;

    @Column(name = "is_published")
    private Boolean isPublic = false;


    public Image(String name, String link, String fileType, byte[] imageData, News news) {
        this.name = name;
        this.link = link;
        this.fileType = fileType;
        this.imageData = imageData;
        this.news = news;
    }

    public Image(String name, String link, String fileType, Boolean isMainImage, News news) {
        this.name = name;
        this.link = link;
        this.fileType = fileType;
        this.isMainImage = isMainImage;
        this.news = news;
    }

    public Image(String name, String link, String fileType, byte[] imageData, News news, Boolean isMainImage) {
        this.name = name;
        this.link = link;
        this.fileType = fileType;
        this.imageData = imageData;
        this.news = news;
        this.isMainImage = isMainImage;
    }

    public Image(String id, String name, String link, String fileType, byte[] imageData, Boolean isMainImage, News news) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.fileType = fileType;
        this.imageData = imageData;
        this.isMainImage = isMainImage;
        this.news = news;
    }

}
