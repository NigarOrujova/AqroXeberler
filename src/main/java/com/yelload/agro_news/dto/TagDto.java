package com.yelload.agro_news.dto;

import java.util.List;

public class TagDto {
    private Long id;
    private String name;
    private List<NewsDto> newsDtos;

    public TagDto(Long id, String name, List<NewsDto> newsDtos) {
        this.id = id;
        this.name = name;
        this.newsDtos = newsDtos;
    }

    public TagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public TagDto(String name, List<NewsDto> newsDtos) {
        this.name = name;
        this.newsDtos = newsDtos;
    }

    public TagDto() {
    }

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

    public List<NewsDto> getNewsDtos() {
        return newsDtos;
    }

    public void setNewsDtos(List<NewsDto> newsDtos) {
        this.newsDtos = newsDtos;
    }
}
