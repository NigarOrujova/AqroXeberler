package com.yelload.agro_news.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    private String id;
    private String name;
    private String link;
    private String fileType;
    @JsonIgnore
    private byte[] imageData;
    private Boolean isMainImage;
    private NewsDto newsDto;

    public ImageDto(String id, String name, String link, String fileType, Boolean isMainImage) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.fileType = fileType;
        this.isMainImage = isMainImage;
    }

    public ImageDto(String name, String link, String fileType, byte[] imageData, Boolean isMainImage) {
        this.name = name;
        this.link = link;
        this.fileType = fileType;
        this.imageData = imageData;
        this.isMainImage = isMainImage;
    }

    public ImageDto(String name, String link, String fileType, byte[] imageData, Boolean isMainImage, NewsDto newsDto) {
        this.name = name;
        this.link = link;
        this.fileType = fileType;
        this.imageData = imageData;
        this.isMainImage = isMainImage;
        this.newsDto = newsDto;
    }

    public ImageDto(String id, String name, String link, String fileType, byte[] imageData, Boolean isMainImage) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.fileType = fileType;
        this.imageData = imageData;
        this.isMainImage = isMainImage;
    }

}
