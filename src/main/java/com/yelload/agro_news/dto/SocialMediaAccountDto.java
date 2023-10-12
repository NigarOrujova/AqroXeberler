package com.yelload.agro_news.dto;


public class SocialMediaAccountDto {
    private Long id;
    private String name;
    private String link;

    public SocialMediaAccountDto(Long id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }
    public SocialMediaAccountDto(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public SocialMediaAccountDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
